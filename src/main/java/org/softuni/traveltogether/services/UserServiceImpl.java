package org.softuni.traveltogether.services;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.common.exception.ExceptionUtils;
import org.softuni.traveltogether.config.WebConstants;
import org.softuni.traveltogether.domain.entities.Role;
import org.softuni.traveltogether.domain.entities.Travel;
import org.softuni.traveltogether.domain.entities.TravelRequest;
import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.domain.models.binding.UserRegisterBindingModel;
import org.softuni.traveltogether.domain.models.service.TravelRequestServiceModel;
import org.softuni.traveltogether.domain.models.service.UserServiceModel;
import org.softuni.traveltogether.errorHandling.exceptions.EntityNotFoundException;
import org.softuni.traveltogether.errorHandling.exceptions.UserEmailAlreadyUsedException;
import org.softuni.traveltogether.errorHandling.exceptions.UserException;
import org.softuni.traveltogether.errorHandling.exceptions.UsernameAlreadyUsedException;
import org.softuni.traveltogether.repositories.RoleRepository;
import org.softuni.traveltogether.repositories.UserRepository;
import org.softuni.traveltogether.specific.UserRole;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final String PROFILE_PICTURE_CLOUD_FOLDER = "travel-together/profiles/";
    private static final int MONTHS_BEFORE_MARK_INACTIVE = 6;
    private static final int MONTHS_BEFORE_DELETE_INACTIVE = 12;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CloudService cloudService;
    private final SessionRegistry sessionRegistry;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, Validator validator, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, CloudService cloudService, SessionRegistry sessionRegistry) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.cloudService = cloudService;
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public String saveUser(UserServiceModel userServiceModel) {
        Optional<User> userCandidate;
        User user;
        if(userServiceModel.getId() != null
                && (userCandidate = this.userRepository.findById(userServiceModel.getId())).isPresent()) {
            user = userCandidate.get();
            this.modelMapper.map(userServiceModel, user);
        } else {
            user = this.modelMapper.map(userServiceModel, User.class);
            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
            Set<Role> userRoleSet = new HashSet<>();
            userRoleSet.add(roleRepository.findFirstByAuthority(UserRole.ROLE_USER.toString()));
            user.setAuthorities(userRoleSet);
        }

        Set<ConstraintViolation<User>> errors = this.validator.validate(user);
        if(!errors.isEmpty()) {
            StringBuilder messageBuilder = new StringBuilder("Invalid properties of user ")
                    .append(errors.stream().findFirst().get().getRootBean())
                    .append("\r\n");
            for (ConstraintViolation<User> error : errors) {
                messageBuilder
                        .append("\t")
                        .append(error.getPropertyPath().toString())
                        .append(": ")
                        .append(error.getInvalidValue()).append("\r\n");
            }

            throw new UserException(messageBuilder.toString());
        }

        try{
            user = this.userRepository.saveAndFlush(user);
            return user.getId();
        } catch (Exception e) {
            String rootCause = ExceptionUtils.getRootCauseMessage(e).toLowerCase();
            if(rootCause.contains(WebConstants.UNIQUE_USERNAME_CONSTRAINT_NAME.toLowerCase()))
                throw new UsernameAlreadyUsedException(userServiceModel);
            else if(rootCause.contains(WebConstants.UNIQUE_EMAIL_CONSTRAINT_NAME.toLowerCase()))
                throw new UserEmailAlreadyUsedException(userServiceModel);
            else
                throw new UserException("Saving travel failed.", e);
        }
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        User user = (User)this.loadUserByUsername(username);
        if(user == null)
            throw new EntityNotFoundException("There is no user with username: " + username);
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public Set<TravelRequestServiceModel> getAllRequests(String username) {
        User user = this.userRepository.findFirstByUsername(username);
        if(user != null) {
            Set<TravelRequestServiceModel> requests = new HashSet<>();
            for (Travel travel : user.getTravels()) {
                for (TravelRequest travelRequest : travel.getRequests()) {
                    requests.add(this.modelMapper.map(travelRequest, TravelRequestServiceModel.class));
                }
            }
            return requests;
        } else {
            throw new EntityNotFoundException("There is no user with username: " + username);
        }
    }

    @Override
    public void updateProfilePicture(UserServiceModel userServiceModel, MultipartFile pictureFile) throws IllegalArgumentException {
        if(userServiceModel.getUsername() == null) {
            throw new IllegalArgumentException();
        }

        if(pictureFile == null)
            return;

        String pictureId = PROFILE_PICTURE_CLOUD_FOLDER + userServiceModel.getUsername();

        try {
            this.cloudService.deleteImage(pictureId);
            String pictureUrl = this.cloudService.uploadImage(
                    pictureFile,
                    pictureId
            );
            userServiceModel.setProfilePictureLink(pictureUrl);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String changeUserRole(String action, String username) {
        if(action == null)
            throw new IllegalArgumentException();

        User user = this.userRepository.findFirstByUsername(username);
        String role = UserRole.ROLE_INVALID.name();
        if(user != null) {
            if(user.getAuthorities().stream().noneMatch(r -> r.getAuthority().equals(UserRole.ROLE_ROOT.name()))) {
                if(action.toLowerCase().equals("promote")) {
                    user.getAuthorities().add(this.roleRepository.findFirstByAuthority(UserRole.ROLE_ADMIN.name()));
                    role = UserRole.ROLE_ADMIN.name();
                } else if(action.toLowerCase().equals("demote")){
                    user.getAuthorities().removeIf(r -> r.getAuthority().equals(UserRole.ROLE_ADMIN.name()));
                    role = UserRole.ROLE_USER.name();
                    this.kickUser(username);
                }
                this.userRepository.saveAndFlush(user);
            }
            return role.split("_")[1];
        } else
            throw new EntityNotFoundException("There is no user with username: " + username);
    }

    @Override
    public void updateActivity(String username, LocalDateTime lastActive) {
        User user = this.userRepository.findFirstByUsername(username);
        if(user != null) {
            user.setLastActive(lastActive);
            this.userRepository.save(user);
        } else throw new EntityNotFoundException("There is no user with username: " + username);
    }

    @Override
    public void kickUser(String username) {
        UserDetails principal =
                (UserDetails) this.sessionRegistry.getAllPrincipals()
                        .stream()
                        .filter(p -> (p instanceof User) && ((UserDetails)p).getUsername().equals(username))
                        .findFirst()
                        .orElse(null);

        if(principal != null) {
            this.sessionRegistry.getAllSessions(principal, true).forEach(SessionInformation::expireNow);
        } else throw new EntityNotFoundException("There is no user with username: " + username);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findFirstByUsername(s);
        if(user == null)
            throw new UsernameNotFoundException("There is no user with username: " + s);
        if(!user.isEnabled()) {
            throw new DisabledException("User " + s + " is currently disabled");
        }
        if(!user.isAccountNonExpired())
            throw new AccountExpiredException("User" + s + " is with expired account");

        return user;
    }

    @Async
    @Scheduled(cron = "0 0 3 15 * *")
    protected void handleInactiveUsers() {
        LocalDateTime startPoint = LocalDateTime.now();
        List<User> toUpdate = new ArrayList<>();
        List<User> toDelete = new ArrayList<>();
        for (User user : this.userRepository.findAll()) {
            if(user.getLastActive() != null){
                if(user.getLastActive().plusMonths(MONTHS_BEFORE_DELETE_INACTIVE).isAfter(startPoint)) {
                    toDelete.add(user);
                } else if(user.getLastActive().plusMonths(MONTHS_BEFORE_MARK_INACTIVE).isAfter(startPoint)){
                    user.setAccountNonExpired(false);
                    toUpdate.add(user);
                }
            }
        }
        this.userRepository.saveAll(toUpdate);
        this.userRepository.deleteAll(toDelete);
    }
}
