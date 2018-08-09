package org.softuni.traveltogether.domain.projections;

import org.softuni.traveltogether.domain.entities.Destination;
import org.softuni.traveltogether.domain.entities.Travel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(name = "travelProjection", types = {Travel.class})
public interface TravelProjection {

    String getId();

    @Value("#{@getModelMapper.map(target, T(org.softuni.traveltogether.domain.models.service.TravelServiceModel)).getShortDescription()}")
    String getShortDescription();

    LocalDateTime getPublishedAt();

    LocalDateTime getDepartureTime();

    Destination getFromDestination();

    Destination getToDestination();

    @Value("#{target.publisher.firstName} #{target.publisher.lastName}")
    String getPublisher();
}
