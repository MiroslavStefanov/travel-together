package org.softuni.traveltogether.domain.models.view;

public class TravelRequestViewModel {
    private String id;
    private UserLinkViewModel user;
    private TravelLinkViewModel travel;

    public TravelRequestViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserLinkViewModel getUser() {
        return user;
    }

    public void setUser(UserLinkViewModel user) {
        this.user = user;
    }

    public TravelLinkViewModel getTravel() {
        return travel;
    }

    public void setTravel(TravelLinkViewModel travel) {
        this.travel = travel;
    }
}
