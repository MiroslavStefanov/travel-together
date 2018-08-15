package org.softuni.traveltogether.domain.models.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class TravelCreateBindingModel {
    private String from;
    private String to;
    private LocalDateTime departureTime;
    private String description;

    public TravelCreateBindingModel() {
    }

    @NotEmpty(message = "{org.softuni.travel-together.validation.Field.empty}")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @NotEmpty(message = "{org.softuni.travel-together.validation.Field.empty}")
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @NotNull(message = "{org.softuni.travel-together.validation.Field.empty}")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
