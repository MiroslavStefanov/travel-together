package org.softuni.traveltogether.services;

import org.softuni.traveltogether.domain.models.service.DestinationServiceModel;

import java.util.List;

public interface DestinationService {

    String saveDestination(DestinationServiceModel destinationServiceModel);

    void deleteDestination(String id);

    DestinationServiceModel getDestination(String id);

    DestinationServiceModel findDestinationByName(String name);

    List<String> getAllDestinationsNames();

    List<DestinationServiceModel> getAllWithMinTravels(long minTravels);
}
