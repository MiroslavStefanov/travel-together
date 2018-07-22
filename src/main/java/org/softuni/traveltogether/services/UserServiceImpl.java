package org.softuni.traveltogether.services;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.domain.models.binding.UserBindingModel;
import org.softuni.traveltogether.domain.models.service.UserServiceModel;
import org.softuni.traveltogether.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Validator validator;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, Validator validator, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean saveUser(UserBindingModel userBindingModel) {
        Set<ConstraintViolation<UserBindingModel>> errors = this.validator.validate(userBindingModel);
        if(!errors.isEmpty()) {
            //send errors data
            return false;
        }

        try{
            User user = this.modelMapper.map(userBindingModel, User.class);
            user = this.userRepository.save(user);
        } catch (Exception e) {
            //better handling of errors
            e.printStackTrace();
            return false;
        }

        return true;
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
        return user;
    }
}
