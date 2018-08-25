package org.softuni.traveltogether.domain.models.view;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UserProfileViewModel {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profilePictureLink;
    private Set<TravelRequestViewModel> requests;
    private Set<TravelLinkViewModel> myTravels;
    private Set<TravelLinkViewModel> attendedTravels;

    public UserProfileViewModel() {
        requests = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Set<TravelRequestViewModel> getRequests() {
        return Collections.unmodifiableSet(requests);
    }

    public void setRequests(Set<TravelRequestViewModel> requests) {
        this.requests = requests;
    }

    public Set<TravelLinkViewModel> getMyTravels() {
        return myTravels;
    }

    public void setMyTravels(Set<TravelLinkViewModel> myTravels) {
        this.myTravels = myTravels;
    }

    public Set<TravelLinkViewModel> getAttendedTravels() {
        return attendedTravels;
    }

    public void setAttendedTravels(Set<TravelLinkViewModel> attendedTravels) {
        this.attendedTravels = attendedTravels;
    }

}
