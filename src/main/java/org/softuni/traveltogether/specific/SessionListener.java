package org.softuni.traveltogether.specific;

import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.domain.models.service.TravelRequestServiceModel;
import org.softuni.traveltogether.services.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SessionListener {
    private final UserRequestManager userRequestManager;

    private final UserService userService;

    public SessionListener(UserRequestManager userRequestManager, UserService userService) {
        this.userRequestManager = userRequestManager;
        this.userService = userService;
    }

    @EventListener
    @Async
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Set<TravelRequestServiceModel> requests = this.userService.getAllRequests(event.getAuthentication().getName());
        this.userRequestManager.addUser(event.getAuthentication().getName(), requests);
    }


    @EventListener
    public void onSessionDestroyed(SessionDestroyedEvent event) {
        for (SecurityContext securityContext : event.getSecurityContexts()) {
            User user = (User)securityContext.getAuthentication().getPrincipal();
            System.out.println(user.getUsername());
        }
    }
}
