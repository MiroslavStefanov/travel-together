package org.softuni.traveltogether.services;

import org.softuni.traveltogether.domain.models.binding.UserRegisterBindingModel;
import org.softuni.traveltogether.domain.models.service.TravelRequestServiceModel;
import org.softuni.traveltogether.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService extends UserDetailsService {

    void saveUser(UserRegisterBindingModel userRegisterBindingModel);

    UserServiceModel findUserByUsername(String username);

    Set<TravelRequestServiceModel> getAllRequests(String username);
}
