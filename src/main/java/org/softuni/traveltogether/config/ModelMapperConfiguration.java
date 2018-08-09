package org.softuni.traveltogether.config;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.entities.Travel;
import org.softuni.traveltogether.domain.models.binding.TravelCreateBindingModel;
import org.softuni.traveltogether.domain.models.service.TravelServiceModel;
import org.softuni.traveltogether.domain.models.view.TravelAllViewModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {
    private final ModelMapper mapper;

    public ModelMapperConfiguration() {
        mapper = new ModelMapper();
        this.configure();
    }

    @Bean
    public ModelMapper getModelMapper() {
        return this.mapper;
    }

    private void configure() {
        //mapping configuration

        //TRAVEL
        this.travelServiceMapping();
        this.travelCreateBindingMapping();
        this.travelAllViewMapping();
    }

    //TRAVEL
    private void travelServiceMapping() {
        this.mapper.createTypeMap(Travel.class, TravelServiceModel.class)
                .addMappings(m -> m.map(t->t.getPublisher().getFullName(), TravelServiceModel::setPublisher));
    }

    private void travelCreateBindingMapping() {
        this.mapper.createTypeMap(TravelCreateBindingModel.class, Travel.class)
                .addMappings(m -> {
                    m.skip(Travel::setFromDestination);
                    m.skip(Travel::setToDestination);
                });
    }

    private void travelAllViewMapping() {
        this.mapper.createTypeMap(TravelServiceModel.class, TravelAllViewModel.class)
                .addMappings(m -> {
                    m.map(tsm -> tsm.getFromDestination().getName(), TravelAllViewModel::setFrom);
                    m.map(tsm -> tsm.getToDestination().getName(), TravelAllViewModel::setTo);
                });
    }
}
