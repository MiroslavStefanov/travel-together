package org.softuni.traveltogether.domain.models.service;

import org.softuni.traveltogether.domain.models.view.UserLinkViewModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class TravelServiceModel {
    private String id;
    private String description;
    private LocalDateTime publishedAt;
    private LocalDateTime departureTime;
    private DestinationServiceModel fromDestination;
    private DestinationServiceModel toDestination;
    private UserLinkViewModel publisher;
    private Set<UserLinkViewModel> attendants;
    private Set<TravelRequestServiceModel> requests;

    public TravelServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public DestinationServiceModel getFromDestination() {
        return fromDestination;
    }

    public void setFromDestination(DestinationServiceModel fromDestination) {
        this.fromDestination = fromDestination;
    }

    public DestinationServiceModel getToDestination() {
        return toDestination;
    }

    public void setToDestination(DestinationServiceModel toDestination) {
        this.toDestination = toDestination;
    }

    public UserLinkViewModel getPublisher() {
        return publisher;
    }

    public void setPublisher(UserLinkViewModel publisher) {
        this.publisher = publisher;
    }

    public Set<UserLinkViewModel> getAttendants() {
        return attendants;
    }

    public void setAttendants(Set<UserLinkViewModel> attendants) {
        this.attendants = attendants;
    }

    public Set<TravelRequestServiceModel> getRequests() {
        return requests;
    }

    public void setRequests(Set<TravelRequestServiceModel> requests) {
        this.requests = requests;
    }
}
