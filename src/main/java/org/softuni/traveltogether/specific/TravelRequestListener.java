package org.softuni.traveltogether.specific;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.entities.TravelRequest;
import org.softuni.traveltogether.domain.models.service.TravelRequestServiceModel;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@RepositoryEventHandler
@Component
public class TravelRequestListener{
    private final UserRequestManager userRequestManager;
    private final ModelMapper modelMapper;

    public TravelRequestListener(UserRequestManager userRequestManager, ModelMapper modelMapper) {
        this.userRequestManager = userRequestManager;
        this.modelMapper = modelMapper;
    }

    @HandleAfterCreate
    public void onCreate(TravelRequest travelRequest){
        this.userRequestManager.addRequest(this.modelMapper.map(travelRequest, TravelRequestServiceModel.class));
    }

    @HandleAfterDelete
    public void onDelete(TravelRequest travelRequest) {
        this.userRequestManager.removeRequest(this.modelMapper.map(travelRequest, TravelRequestServiceModel.class));
    }
}
