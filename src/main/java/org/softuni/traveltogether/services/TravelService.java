package org.softuni.traveltogether.services;

import org.softuni.traveltogether.domain.models.service.TravelServiceModel;

public interface TravelService {

    String saveTravel(TravelServiceModel travelServiceModel);

    void deleteTravel(String travelId);

    TravelServiceModel getTravel(String id);

    void manageAttendant(String travelId, String attendantId, String action);
}
