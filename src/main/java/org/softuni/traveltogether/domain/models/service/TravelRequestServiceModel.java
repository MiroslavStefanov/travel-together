package org.softuni.traveltogether.domain.models.service;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof TravelRequestServiceModel))
            return false;

        return this.getId().equals(((TravelRequestServiceModel) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
