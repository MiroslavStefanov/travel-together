package org.softuni.traveltogether.domain.models.view;

public class TravelRequestViewModel {
    private String id;
    private String userId;

    public TravelRequestViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
