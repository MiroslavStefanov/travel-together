package org.softuni.traveltogether.web.intercpetrors;

import org.softuni.traveltogether.common.authentication.AuthenticationWrapper;
import org.softuni.traveltogether.domain.models.service.UserServiceModel;
import org.softuni.traveltogether.services.UserService;
import org.softuni.traveltogether.specific.UserRequestManager;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticatedUserDataInterceptor implements HandlerInterceptor {
    private static final String REQUEST_COUNT_NAME =  "requestCount";
    private static final String CURRENT_USER_ID_NAME = "currentUserId";

    private final UserService userService;
    private final UserRequestManager userRequestManager;
    private final AuthenticationWrapper authenticationWrapper;

    public AuthenticatedUserDataInterceptor(UserService userService, UserRequestManager userRequestManager, AuthenticationWrapper authenticationWrapper) {
        this.userService = userService;
        this.userRequestManager = userRequestManager;
        this.authenticationWrapper = authenticationWrapper;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if(handler instanceof HandlerMethod
                && modelAndView != null
                && !modelAndView.getViewName().startsWith("redirect:")
                && authenticationWrapper.isAuthenticated()) {
            String username = this.authenticationWrapper.getAuthentication().getName();
            UserServiceModel currentUser = this.userService.findUserByUsername(username);
            if(currentUser != null) {
                modelAndView.addObject(REQUEST_COUNT_NAME, this.userRequestManager.getRequestCount(currentUser.getUsername()));
                modelAndView.addObject(CURRENT_USER_ID_NAME, currentUser.getId());
            }
        }
    }
}
