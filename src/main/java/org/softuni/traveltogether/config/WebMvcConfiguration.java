package org.softuni.traveltogether.config;

import org.softuni.traveltogether.web.intercpetrors.AuthenticatedUserDataInterceptor;
import org.softuni.traveltogether.web.intercpetrors.UserRequestsInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final AuthenticatedUserDataInterceptor authenticatedUserDataInterceptor;
    private final UserRequestsInterceptor userRequestsInterceptor;


    @Autowired
    public WebMvcConfiguration(AuthenticatedUserDataInterceptor authenticatedUserDataInterceptor, UserRequestsInterceptor userRequestsInterceptor) {
        this.authenticatedUserDataInterceptor = authenticatedUserDataInterceptor;
        this.userRequestsInterceptor = userRequestsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticatedUserDataInterceptor);
        registry.addInterceptor(userRequestsInterceptor).addPathPatterns("/profile/**").excludePathPatterns("/profile/**/edit");
    }
}
