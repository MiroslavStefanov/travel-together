package org.softuni.traveltogether.services;

import org.softuni.traveltogether.domain.models.binding.UserBindingModel;
import org.softuni.traveltogether.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean saveUser(UserBindingModel userBindingModel);

    UserServiceModel findUserByUsername(String username);

}
