package org.softuni.traveltogether.domain.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private List<Comment> comments;

    public Travel() {
        this.attendants = new HashSet<>();
        this.comments = new ArrayList<>();
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

    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @Column(nullable = false)
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

    @ManyToOne
    public Destination getFromDestination() {
        return fromDestination;
    }

    public void setFromDestination(Destination fromDestination) {
        this.fromDestination = fromDestination;
    }

    @NotNull
    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Destination getToDestination() {
        return toDestination;
    }

    public void setToDestination(Destination toDestination) {
        this.toDestination = toDestination;
    }

    @NotNull
    @ManyToOne(optional = false)
    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    @ManyToMany
    public Set<User> getAttendants() {
        return attendants;
    }

    public void setAttendants(Set<User> attendants) {
        this.attendants = attendants;
    }

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
