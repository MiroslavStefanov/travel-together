package org.softuni.traveltogether.specific;

import org.softuni.traveltogether.domain.models.service.TravelRequestServiceModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class UserRequestManager {
    private Map<String, Set<TravelRequestServiceModel>> requestMap;

    public UserRequestManager() {
        this.requestMap = new HashMap<>();
    }

    public Integer getRequestCount(String username) {
        if(this.requestMap.containsKey(username)) {
            return this.requestMap.get(username).size();
        }
        return 0;
    }

    public Set<TravelRequestServiceModel> getRequests(String username) {
        return requestMap.getOrDefault(username, null);
    }

    public void addRequest(TravelRequestServiceModel travelRequest) {
        String username = travelRequest.getTravel().getPublisher().getUsername();
        if(this.requestMap.containsKey(username)) {
            this.getRequests(username).add(travelRequest);
        }
    }

    public void removeRequest(TravelRequestServiceModel travelRequest) {
        String username = travelRequest.getTravel().getPublisher().getUsername();
        Set<TravelRequestServiceModel> requests = this.getRequests(username);
        if(requests != null) {
            requests.remove(travelRequest);
            this.requestMap.put(username, requests);
        }
    }

    public void addUser(String username, Set<TravelRequestServiceModel> requests) {
        this.requestMap.putIfAbsent(username, requests);
    }
}
