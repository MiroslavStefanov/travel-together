package org.softuni.traveltogether.domain.models.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TravelCreateBindingModel {
    private String from;
    private String to;
    private LocalDateTime departureTime;
    private String description;

    public TravelCreateBindingModel() {
    }

    @NotNull
    @NotEmpty
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @NotNull
    @NotEmpty
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

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
