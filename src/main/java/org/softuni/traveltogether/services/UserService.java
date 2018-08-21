package org.softuni.traveltogether.services;

import org.softuni.traveltogether.domain.models.binding.UserRegisterBindingModel;
import org.softuni.traveltogether.domain.models.service.TravelRequestServiceModel;
import org.softuni.traveltogether.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

public interface UserService extends UserDetailsService {

    void saveUser(UserServiceModel userModel);

    UserServiceModel findUserByUsername(String username);

    Set<TravelRequestServiceModel> getAllRequests(String username);

    void updateProfilePicture(UserServiceModel userServiceModel, MultipartFile pictureFile) throws IllegalArgumentException;

    String changeUserRole(String action, String username);

    void updateActivity(String username, LocalDateTime lastActive);

    void kickUser(String username);
}
