package org.softuni.traveltogether.domain.models.service;

import java.time.LocalDateTime;

public class TravelServiceModel {
    private static final short DESCRIPTION_SHORT_LIMIT = 120;
    private static final short SPACE_SEARCH_RANGE = 15;

    private String id;
    private String description;
    private LocalDateTime publishedAt;
    private LocalDateTime departureTime;
    private DestinationServiceModel fromDestination;
    private DestinationServiceModel toDestination;
    private String publisher;

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

    public String getShortDescription() {
        if(this.description != null && this.description.length() > DESCRIPTION_SHORT_LIMIT) {
            int spaceIndx = this.description.indexOf(' ', DESCRIPTION_SHORT_LIMIT - SPACE_SEARCH_RANGE);
            if(spaceIndx - SPACE_SEARCH_RANGE > DESCRIPTION_SHORT_LIMIT)
                spaceIndx = DESCRIPTION_SHORT_LIMIT;
            return this.description.substring(0, spaceIndx) + "...";
        }

        return this.description;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
