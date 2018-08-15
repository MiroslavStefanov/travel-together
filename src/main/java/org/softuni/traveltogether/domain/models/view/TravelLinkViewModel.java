package org.softuni.traveltogether.domain.models.view;

import java.time.LocalDateTime;

public class TravelLinkViewModel {
    private String id;
    private String name;
    private LocalDateTime departureTime;

    public TravelLinkViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
}
