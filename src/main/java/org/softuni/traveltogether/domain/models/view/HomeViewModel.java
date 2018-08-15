package org.softuni.traveltogether.domain.models.view;

import java.util.Set;

public class HomeViewModel {
    private Set<TravelLinkViewModel> myTravels;
    private Set<TravelLinkViewModel> attendedTravels;


    public HomeViewModel() {
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
