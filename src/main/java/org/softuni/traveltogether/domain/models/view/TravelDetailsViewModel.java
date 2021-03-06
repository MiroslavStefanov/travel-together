package org.softuni.traveltogether.domain.models.view;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TravelDetailsViewModel {
    private String id;
    private DestinationLinkViewModel fromDestination;
    private DestinationLinkViewModel toDestination;
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

    public DestinationLinkViewModel getFromDestination() {
        return fromDestination;
    }

    public void setFromDestination(DestinationLinkViewModel fromDestination) {
        this.fromDestination = fromDestination;
    }

    public DestinationLinkViewModel getToDestination() {
        return toDestination;
    }

    public void setToDestination(DestinationLinkViewModel toDestination) {
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
        Set<UserLinkViewModel> attendantsWithPublisher = new LinkedHashSet<>(){{
            add(publisher);
        }};
        attendantsWithPublisher.addAll(
                this.attendants
                        .stream()
                        .sorted(Comparator.comparing(UserLinkViewModel::getFullName))
                        .collect(Collectors.toSet())
        );
        return attendantsWithPublisher;
    }

    public void setAttendants(Set<UserLinkViewModel> attendants) {
        this.attendants = attendants;
    }

    public Set<TravelRequestViewModel> getRequests() {
        return Collections.unmodifiableSet(requests);
    }

    public void setRequests(Set<TravelRequestViewModel> requests) {
        this.requests = requests;
    }

    public Set<String> getAllRequestingUsernames() {
        return this.requests.stream().map(r -> r.getUser().getUsername()).collect(Collectors.toUnmodifiableSet());
    }

    public Set<String> getAllParticipatedUsernames() {
        return this.getAttendants().stream().map(UserLinkViewModel::getUsername).collect(Collectors.toUnmodifiableSet());
    }

    public String getRequestByUsername(String username) {
        return this.requests.stream().filter(r -> r.getUser().getUsername().equals(username)).findFirst().map(TravelRequestViewModel::getId).orElse(null);
    }
}
