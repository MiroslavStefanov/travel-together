package org.softuni.traveltogether.domain.models.service;

import org.softuni.traveltogether.domain.models.view.TravelLinkViewModel;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServiceModel {
    private static String DEFAULT_PROFILE_PICTURE_LINK = "/assets/profile-default.png";

    private String id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profilePictureLink;
    private Set<TravelLinkViewModel> travels;
    private Set<TravelLinkViewModel> attendedTravels;

    public UserServiceModel() {
        this.travels = new HashSet<>();
        this.attendedTravels = new HashSet<>();
        this.profilePictureLink = DEFAULT_PROFILE_PICTURE_LINK;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePictureLink() {
        return profilePictureLink;
    }

    public void setProfilePictureLink(String profilePictureLink) {
        this.profilePictureLink = profilePictureLink;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public Set<TravelLinkViewModel> getTravels() {
        return travels.stream().filter(t -> t.getDepartureTime().isAfter(LocalDateTime.now())).collect(Collectors.toUnmodifiableSet());
    }

    public void setTravels(Set<TravelLinkViewModel> travels) {
        this.travels = travels;
    }

    public Set<TravelLinkViewModel> getAttendedTravels() {
        return attendedTravels
                .stream()
                .filter(t -> t.getDepartureTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toUnmodifiableSet());
    }

    public void setAttendedTravels(Set<TravelLinkViewModel> attendedTravels) {
        this.attendedTravels = attendedTravels;
    }
}
