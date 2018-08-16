package org.softuni.traveltogether.domain.models.view;

public class TravelRequestViewModel {
    private String id;
    private UserLinkViewModel user;

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
}
