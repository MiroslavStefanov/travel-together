package org.softuni.traveltogether.domain.models.service;

import java.util.Set;

public class DestinationServiceModel {
    private String id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String description;
    private Long travelCount;
    private Set<String> imageUrls;

    public DestinationServiceModel() {
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTravelCount() {
        return travelCount;
    }

    public void setTravelCount(Long travelCount) {
        this.travelCount = travelCount;
    }

    public Set<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(Set<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
