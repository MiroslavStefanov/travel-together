package org.softuni.traveltogether.domain.models.view;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TravelDetailsViewModel {
    private String id;
    private DestinationViewModel fromDestination;
    private DestinationViewModel toDestination;
    private LocalDateTime departureTime;
    private UserLinkViewModel publisher;
    private LocalDateTime publishedAt;
    private String description;
    private Set<UserLinkViewModel> attendants;
    private Set<TravelRequestViewModel> requests;

    public TravelDetailsViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DestinationViewModel getFromDestination() {
        return fromDestination;
    }

    public void setFromDestination(DestinationViewModel fromDestination) {
        this.fromDestination = fromDestination;
    }

    public DestinationViewModel getToDestination() {
        return toDestination;
    }

    public void setToDestination(DestinationViewModel toDestination) {
        this.toDestination = toDestination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public UserLinkViewModel getPublisher() {
        return publisher;
    }

    public void setPublisher(UserLinkViewModel publisher) {
        this.publisher = publisher;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserLinkViewModel> getAttendants() {
        Set<UserLinkViewModel> attendantsWithPublisher = new HashSet<>(this.attendants);
        attendantsWithPublisher.add(this.publisher);
        return attendantsWithPublisher;
    }

    public void setAttendants(Set<UserLinkViewModel> attendants) {
        this.attendants = attendants;
    }

    public Set<TravelRequestViewModel> getRequests() {
        return requests;
    }

    public void setRequests(Set<TravelRequestViewModel> requests) {
        this.requests = requests;
    }

    public Set<String> getAllRequestingUserIds() {
        return this.requests.stream().map(r -> r.getUser().getId()).collect(Collectors.toSet());
    }

    public Set<String> getAllParticipatedUserIds() {
        return this.getAttendants().stream().map(UserLinkViewModel::getId).collect(Collectors.toSet());
    }

    public String getRequestByUserId(String userId) {
        return this.requests.stream().filter(r -> r.getUser().getId().equals(userId)).findFirst().map(TravelRequestViewModel::getId).orElse(null);
    }
}
