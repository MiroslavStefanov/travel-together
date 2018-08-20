package org.softuni.traveltogether.domain.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DestinationBindingModel {
    private String name;
    private Double latitude;
    private Double longitude;

    public DestinationBindingModel() {
    }

    @NotEmpty(message = "{org.softuni.travel-together.validation.Field.empty}")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "{org.softuni.travel-together.validation.Field.empty}")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = Double.parseDouble(latitude);
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @NotNull(message = "{org.softuni.travel-together.validation.Field.empty}")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = Double.parseDouble(longitude);
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
