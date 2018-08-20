package org.softuni.traveltogether.services;

import org.softuni.traveltogether.domain.models.binding.TravelCreateBindingModel;
import org.softuni.traveltogether.domain.models.service.TravelServiceModel;

import java.util.List;

public interface TravelService {

    String saveTravel(TravelCreateBindingModel travelCreateBindingModel);

    String editTravel(String travelId, TravelCreateBindingModel travelCreateBindingModel);

    void deleteTravel(String travelId);

    TravelServiceModel getTravel(String id);

    void addAttendant(String travelId, String attendantId);
}
