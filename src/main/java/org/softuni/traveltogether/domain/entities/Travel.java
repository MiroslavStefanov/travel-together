package org.softuni.traveltogether.domain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "travels")
public class Travel {
    private String id;
    private String description;
    private LocalDateTime publishedAt;
    private LocalDateTime departureTime;
    private Destination fromDestination;
    private Destination toDestination;
    private User publisher;
    private Set<User> attendants;
    private Set<TravelRequest> requests;

    public Travel() {
        this.attendants = new HashSet<>();
        this.requests = new HashSet<>();
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @Column(nullable = false, updatable = false)
    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    @NotNull
    @Column(nullable = false)
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    @NotNull
    @ManyToOne
    public Destination getFromDestination() {
        return fromDestination;
    }

    public void setFromDestination(Destination fromDestination) {
        this.fromDestination = fromDestination;
    }

    @NotNull
    @ManyToOne
    public Destination getToDestination() {
        return toDestination;
    }

    public void setToDestination(Destination toDestination) {
        this.toDestination = toDestination;
    }

    @NotNull
    @ManyToOne
    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "travels_attendants",
            joinColumns = @JoinColumn(name = "travel_id"),
            inverseJoinColumns =  @JoinColumn(name = "attendant_id")
    )
    public Set<User> getAttendants() {
        return this.attendants;
    }

    public void setAttendants(Set<User> attendants) {
        this.attendants = attendants;
    }

    @OneToMany(mappedBy = "travel", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Set<TravelRequest> getRequests() {
        return requests;
    }

    public void setRequests(Set<TravelRequest> requests) {
        this.requests = requests;
    }

    @Transient
    public String getName() {
        if(this.toDestination != null)
            return this.toDestination.getName();
        return null;
    }

    public void addAttendant(User attendant) {
        this.attendants.add(attendant);
        attendant.attendTravel(this);
    }

    public void removeAttendant(String attendantId) {
        this.attendants.removeIf(a -> a.getId().equals(attendantId));
    }
}
