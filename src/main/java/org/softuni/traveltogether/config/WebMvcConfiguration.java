package org.softuni.traveltogether.config;

import org.softuni.traveltogether.web.intercpetrors.TravelRequestInterceptor;
import org.softuni.traveltogether.web.intercpetrors.UserRequestInteceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final TravelRequestInterceptor travelRequestInterceptor;
    private final UserRequestInteceptor userRequestInteceptor;


    @Autowired
    public WebMvcConfiguration(TravelRequestInterceptor travelRequestInterceptor, UserRequestInteceptor userRequestInteceptor) {
        this.travelRequestInterceptor = travelRequestInterceptor;
        this.userRequestInteceptor = userRequestInteceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(travelRequestInterceptor);
        registry.addInterceptor(userRequestInteceptor).addPathPatterns("/profile/**").excludePathPatterns("/profile/**/edit");
    }
}
