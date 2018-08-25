package org.softuni.traveltogether.domain.models.view;

import org.softuni.traveltogether.common.crud.AbstractCrudViewModel;
import org.softuni.traveltogether.domain.models.binding.DestinationBindingModel;

public class DestinationCreateViewModel extends AbstractCrudViewModel {
    public DestinationCreateViewModel() {
        super(new DestinationBindingModel());
    }

    public DestinationCreateViewModel(String targetUrl, Object bindingModel) {
        super(targetUrl, bindingModel);
    }
}
