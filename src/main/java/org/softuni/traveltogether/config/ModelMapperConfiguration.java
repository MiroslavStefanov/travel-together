package org.softuni.traveltogether.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.softuni.traveltogether.domain.entities.*;
import org.softuni.traveltogether.domain.models.binding.TravelCreateBindingModel;
import org.softuni.traveltogether.domain.models.service.DestinationServiceModel;
import org.softuni.traveltogether.domain.models.service.TravelRequestServiceModel;
import org.softuni.traveltogether.domain.models.service.TravelServiceModel;
import org.softuni.traveltogether.domain.models.service.UserServiceModel;
import org.softuni.traveltogether.domain.models.view.*;
import org.softuni.traveltogether.repositories.DestinationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfiguration {
    private final ModelMapper mapper;

    private final DestinationRepository destinationRepository;

    public ModelMapperConfiguration(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
        mapper = new ModelMapper();
        this.configure();
    }

    @Bean
    public ModelMapper getModelMapper() {
        return this.mapper;
    }

    private void configure() {
        //adding converters
        this.addConverters();

        //mapping configuration

        //TRAVEL
        travelServiceInverseMapping();
        this.travelCreateBindingMapping();
        this.travelCreateBindingInverseMapping();

        //USER
        this.userServiceInverseMapping();
        this.homeViewMapping();
        this.userProfileViewMapping();

        //DESTINATION
        this.destinationServiceMapping();

    }

    private void addConverters() {
        Converter<Set<User>, Set<UserServiceModel>> userSetServiceCon = ctx -> ctx.getSource()
                .stream()
                .map(u -> this.mapper.map(u, UserServiceModel.class))
                .collect(Collectors.toSet());


        Converter<Set<UserServiceModel>, Set<UserLinkViewModel>> userSetLinkViewCon = ctx -> ctx.getSource()
                .stream()
                .map(u -> this.mapper.map(u, UserLinkViewModel.class))
                .collect(Collectors.toSet());

        Converter<Set<Travel>, Set<TravelServiceModel>> travelSetServiceCon = ctx -> ctx.getSource()
                .stream()
                .map(t -> this.mapper.map(t, TravelServiceModel.class))
                .collect(Collectors.toSet());

        Converter<Set<TravelServiceModel>, Set<TravelLinkViewModel>> travelSetLinkCon = ctx -> ctx.getSource()
                .stream()
                .map(t -> this.mapper.map(t, TravelLinkViewModel.class))
                .collect(Collectors.toSet());

        Converter<Set<TravelRequest>, Set<TravelRequestServiceModel>> travelRequestServiceCon = ctx -> ctx.getSource()
                .stream()
                .map(r -> this.mapper.map(r, TravelRequestServiceModel.class))
                .collect(Collectors.toSet());

        Converter<Set<TravelRequestServiceModel>, Set<TravelRequestViewModel>> travelRequestViewCon = ctx -> ctx.getSource()
                .stream()
                .map(r -> this.mapper.map(r, TravelRequestViewModel.class))
                .collect(Collectors.toSet());

        this.mapper.addConverter(userSetServiceCon);
        this.mapper.addConverter(userSetLinkViewCon);
        this.mapper.addConverter(travelSetServiceCon);
        this.mapper.addConverter(travelSetLinkCon);
        this.mapper.addConverter(travelRequestServiceCon);
        this.mapper.addConverter(travelRequestViewCon);
    }


    //TRAVEL
    private void travelServiceInverseMapping() {
        Converter<String, Destination> destinationConverter = ctx -> this.destinationRepository.findFirstByName(ctx.getSource());

        this.mapper.createTypeMap(TravelServiceModel.class, Travel.class)
                .addMappings(m -> {
                    m.using(destinationConverter).map(tsm -> tsm.getFromDestination().getName(), Travel::setFromDestination);
                    m.using(destinationConverter).map(tsm -> tsm.getToDestination().getName(), Travel::setToDestination);
                    m.skip(Travel::setPublisher);
                    m.skip(Travel::setAttendants);
                    m.skip(Travel::setRequests);
                });
    }

    private void travelCreateBindingMapping() {
        Converter<String, DestinationServiceModel> destinationConverter = ctx -> {
            DestinationServiceModel model = new DestinationServiceModel();
            model.setName(ctx.getSource());
            return model;
        };

        this.mapper.createTypeMap(TravelCreateBindingModel.class, TravelServiceModel.class)
                .addMappings(m -> {
                    m.using(destinationConverter).map(TravelCreateBindingModel::getFrom, TravelServiceModel::setFromDestination);
                    m.using(destinationConverter).map(TravelCreateBindingModel::getTo, TravelServiceModel::setToDestination);
                });
    }

    private void travelCreateBindingInverseMapping() {
        this.mapper.createTypeMap(TravelServiceModel.class, TravelCreateBindingModel.class)
                .addMappings(m -> {
                    m.map(t->t.getFromDestination().getName(), TravelCreateBindingModel::setFrom);
                    m.map(t->t.getToDestination().getName(), TravelCreateBindingModel::setTo);
                });
    }

    //USER
    private void userServiceInverseMapping() {
        this.mapper.createTypeMap(UserServiceModel.class, User.class)
                .addMappings(m -> {
                    m.skip(User::setAttendedTravels);
                    m.skip(User::setTravels);
                });
    }

    private void homeViewMapping() {

    }

    private void userProfileViewMapping() {
        this.mapper.createTypeMap(UserServiceModel.class, UserProfileViewModel.class)
                .addMappings(m -> m.map(UserServiceModel::getTravels, UserProfileViewModel::setMyTravels));
    }

    //DESTINATION
    private void destinationServiceMapping() {
        this.mapper.createTypeMap(Destination.class, DestinationServiceModel.class)
                .addMappings(m -> m.map(d -> d.getTravels().size(), DestinationServiceModel::setTravelCount));
    }

    private void destinationServiceInverseMapping() {
        this.mapper.createTypeMap(DestinationServiceModel.class, Destination.class)
                .addMappings(m -> {
                    m.skip(Destination::setImageUrls);
                });
    }
}
