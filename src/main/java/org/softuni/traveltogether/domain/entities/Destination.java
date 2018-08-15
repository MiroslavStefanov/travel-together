package org.softuni.traveltogether.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "destinations")
public class Destination {
    private String id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Set<Travel> travels;
    private Set<String> imageUrls;

    public Destination() {
        this.travels = new HashSet<>();
    }

    public Destination(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Column(nullable = false)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @NotNull
    @Column(nullable = false)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "toDestination", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Travel> getTravels() {
        return travels;
    }

    public void setTravels(Set<Travel> travels) {
        this.travels = travels;
    }

    @Lob
    @ElementCollection
    public Set<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(Set<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
