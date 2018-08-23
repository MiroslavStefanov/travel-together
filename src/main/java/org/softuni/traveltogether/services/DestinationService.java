package org.softuni.traveltogether.services;

import org.softuni.traveltogether.domain.models.service.DestinationServiceModel;

import java.util.List;

public interface DestinationService {

    String saveDestination(DestinationServiceModel destinationServiceModel);

    DestinationServiceModel findDestinationByName(String name);

    List<String> getAllDestinationsNames();
}
