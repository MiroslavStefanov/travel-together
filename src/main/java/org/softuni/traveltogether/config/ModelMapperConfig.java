package org.softuni.traveltogether.config;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.domain.models.binding.UserBindingModel;
import org.softuni.traveltogether.domain.models.service.UserServiceModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    private final ModelMapper mapper;

    public ModelMapperConfig() {
        mapper = new ModelMapper();
        this.configure();
    }

    @Bean
    public ModelMapper getModelMapper() {
        return this.mapper;
    }

    private void configure() {
        //mapping configuration

        //USER
        this.userBindingMapping();
        this.userServiceMapping();
    }

    //USER
    private void userBindingMapping() {
        this.mapper.createTypeMap(UserBindingModel.class, User.class);
    }

    private void userServiceMapping() {
        this.mapper.createTypeMap(User.class, UserServiceModel.class);
    }
}
