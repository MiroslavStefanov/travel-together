package org.softuni.traveltogether.services;

import org.softuni.traveltogether.domain.models.service.DestinationServiceModel;

import java.util.List;

public interface DestinationService {

    void saveDestination(DestinationServiceModel destinationServiceModel);

    List<String> getAllDestinationsNames();
}
