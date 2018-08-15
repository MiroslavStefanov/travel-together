package org.softuni.traveltogether.domain.models.service;

public class TravelRequestServiceModel {
    private String id;
    private UserServiceModel user;
    private TravelServiceModel travel;

    public TravelRequestServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public TravelServiceModel getTravel() {
        return travel;
    }

    public void setTravel(TravelServiceModel travel) {
        this.travel = travel;
    }
}
