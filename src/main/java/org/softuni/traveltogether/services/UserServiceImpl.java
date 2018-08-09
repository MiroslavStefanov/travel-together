package org.softuni.traveltogether.services;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.domain.models.binding.UserRegisterBindingModel;
import org.softuni.traveltogether.domain.models.service.UserServiceModel;
import org.softuni.traveltogether.repositories.RoleRepository;
import org.softuni.traveltogether.repositories.UserRepository;
import org.softuni.traveltogether.specific.UserRole;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, Validator validator, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void saveUser(UserRegisterBindingModel userRegisterBindingModel) {
        Set<ConstraintViolation<UserRegisterBindingModel>> errors = this.validator.validate(userRegisterBindingModel);
        if(!errors.isEmpty()) {
            //send errors data
            //throw exception
        }

        try{
            User user = this.modelMapper.map(userRegisterBindingModel, User.class);
            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
            user.setAuthorities(new HashSet<>(){{
                add(roleRepository.findFirstByAuthority(UserRole.ROLE_USER.toString()));
            }});
            user = this.userRepository.save(user);
        } catch (Exception e) {
            //better handling of errors
            e.printStackTrace();
        }
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        User user = (User)this.loadUserByUsername(username);
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findFirstByUsername(s);
        if(user == null)
            throw new UsernameNotFoundException("There is no user with username: " + s);
        if(!user.isEnabled()) {
            throw new DisabledException("User " + s + " is currently disabled");
        }
        return user;
    }
}
