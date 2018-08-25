package org.softuni.traveltogether.domain.models.view;

import org.softuni.traveltogether.common.crud.AbstractCrudViewModel;
import org.softuni.traveltogether.domain.models.binding.TravelCreateBindingModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TravelCreateViewModel extends AbstractCrudViewModel {
    private List<String> allDestinations;

    public TravelCreateViewModel() {
        super(new TravelCreateBindingModel());
    }

    public TravelCreateViewModel(String targetUrl, TravelCreateBindingModel bindingModel, List<String> allDestinations) {
        super(targetUrl, bindingModel);
        this.allDestinations = allDestinations;
    }

    public List<String> getAllDestinations() {
        return allDestinations;
    }

    public void setAllDestinations(List<String> allDestinations) {
        this.allDestinations = allDestinations;
    }
}
