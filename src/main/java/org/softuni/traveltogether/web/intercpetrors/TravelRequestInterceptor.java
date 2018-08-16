package org.softuni.traveltogether.web.intercpetrors;

import org.softuni.traveltogether.common.authentication.AuthenticationWrapper;
import org.softuni.traveltogether.specific.UserRequestManager;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TravelRequestInterceptor implements HandlerInterceptor {
    private static final String REQUEST_COUNT_NAME =  "requestCount";

    private final UserRequestManager userRequestManager;
    private final AuthenticationWrapper authenticationWrapper;

    public TravelRequestInterceptor(UserRequestManager userRequestManager, AuthenticationWrapper authenticationWrapper) {
        this.userRequestManager = userRequestManager;
        this.authenticationWrapper = authenticationWrapper;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if(handler instanceof HandlerMethod && !modelAndView.getViewName().startsWith("redirect:")) {
            modelAndView.addObject(REQUEST_COUNT_NAME, this.userRequestManager.getRequestCount(this.authenticationWrapper.getAuthentication().getName()));
        }
    }
}
