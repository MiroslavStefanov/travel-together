package org.softuni.traveltogether.domain.models.view;

import java.util.Collections;
import java.util.Set;

public class DestinationDetailsViewModel {
    private String id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String description;
    private Set<String> imageUrls;
    private Long travelCount;

    public DestinationDetailsViewModel() {
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
        return Collections.unmodifiableSet(this.imageUrls);
    }

    public void setImageUrls(Set<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
