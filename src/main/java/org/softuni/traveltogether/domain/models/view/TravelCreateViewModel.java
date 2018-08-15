package org.softuni.traveltogether.domain.models.view;

import org.softuni.traveltogether.domain.models.binding.TravelCreateBindingModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TravelCreateViewModel {
    private String targetUrl;
    private TravelCreateBindingModel bindingModel;
    private List<String> allDestinations;

    public TravelCreateViewModel(String targetUrl, TravelCreateBindingModel bindingModel, List<String> allDestinations) {
        this.targetUrl = targetUrl;
        this.bindingModel = bindingModel;
        this.allDestinations = allDestinations;
    }

    public String getAction() {
        if(this.targetUrl != null) {
            String[] splitted = this.targetUrl.split("/");
            if(splitted.length > 0) {
                return splitted[splitted.length-1];
            }
            return null;
        }
        return null;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @NotNull
    @Valid
    public TravelCreateBindingModel getBindingModel() {
        return bindingModel;
    }

    public void setBindingModel(TravelCreateBindingModel bindingModel) {
        this.bindingModel = bindingModel;
    }

    public List<String> getAllDestinations() {
        return allDestinations;
    }

    public void setAllDestinations(List<String> allDestinations) {
        this.allDestinations = allDestinations;
    }
}
