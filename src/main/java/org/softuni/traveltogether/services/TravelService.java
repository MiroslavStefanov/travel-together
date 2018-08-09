package org.softuni.traveltogether.services;

import org.softuni.traveltogether.domain.models.binding.TravelCreateBindingModel;
import org.softuni.traveltogether.domain.models.service.TravelServiceModel;

import java.util.List;

public interface TravelService {

    String saveTravel(TravelCreateBindingModel travelCreateBindingModel);

    List<TravelServiceModel> get5MostRecent();

}
