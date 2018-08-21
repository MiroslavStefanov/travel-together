package org.softuni.traveltogether.services;

import org.softuni.traveltogether.domain.models.service.DestinationServiceModel;

import java.util.List;

public interface DestinationService {

    void saveDestination(DestinationServiceModel destinationServiceModel);

    DestinationServiceModel findDestinationByName(String name);

    List<String> getAllDestinationsNames();
}
