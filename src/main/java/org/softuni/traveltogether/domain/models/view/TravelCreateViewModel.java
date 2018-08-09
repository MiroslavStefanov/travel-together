package org.softuni.traveltogether.domain.models.view;

import org.softuni.traveltogether.domain.models.binding.TravelCreateBindingModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TravelCreateViewModel {
    private String action;
    private TravelCreateBindingModel bindingModel;
    private List<String> allDestinations;

    public TravelCreateViewModel(String action, TravelCreateBindingModel bindingModel, List<String> allDestinations) {
        this.action = action;
        this.bindingModel = bindingModel;
        this.allDestinations = allDestinations;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
