package org.softuni.traveltogether.services;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.entities.Travel;
import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.domain.models.service.TravelServiceModel;
import org.softuni.traveltogether.errorHandling.exceptions.EntityNotFoundException;
import org.softuni.traveltogether.errorHandling.exceptions.TravelException;
import org.softuni.traveltogether.repositories.TravelRepository;
import org.softuni.traveltogether.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class TravelServiceImpl implements TravelService {
    private final TravelRepository travelRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public TravelServiceImpl(TravelRepository travelRepository, UserRepository userRepository, ModelMapper modelMapper, Validator validator) {
        this.travelRepository = travelRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public String saveTravel(TravelServiceModel travelServiceModel) {
        Optional<Travel> travelCandidate;
        Travel travel;
        if(travelServiceModel.getId() != null
                && (travelCandidate = this.travelRepository.findById(travelServiceModel.getId())).isPresent()) {
            travel = travelCandidate.get();
            this.modelMapper.map(travelServiceModel, travel);
        } else {
            travel = this.modelMapper.map(travelServiceModel, Travel.class);
            User user = this.userRepository.findFirstByUsername(travelServiceModel.getPublisher().getUsername());
            travel.setPublisher(user);
        }

        Set<ConstraintViolation<Travel>> errors = this.validator.validate(travel);
        if(!errors.isEmpty()) {
            StringBuilder messageBuilder = new StringBuilder("Invalid properties of travel ")
                    .append(errors.stream().findFirst().get().getRootBean())
                    .append("\r\n");
            for (ConstraintViolation<Travel> error : errors) {
                messageBuilder
                        .append("\t")
                        .append(error.getPropertyPath().toString())
                        .append(": ")
                        .append(error.getInvalidValue()).append("\r\n");
            }

            throw new TravelException(messageBuilder.toString());
        }

        try{
            travel = this.travelRepository.saveAndFlush(travel);
            return travel.getId();
        } catch (Exception e) {
            throw new TravelException("Saving travel failed.", e);
        }
    }

    @Override
    public void deleteTravel(String travelId) {
        this.travelRepository.deleteById(travelId);
    }

    @Override
    public TravelServiceModel getTravel(String id) {
        return this.travelRepository
                .findById(id)
                .map(t -> this.modelMapper.map(t, TravelServiceModel.class))
                .orElseThrow(() -> new EntityNotFoundException("There is no travel with id: " + id));
    }

    @Override
    public void manageAttendant(String travelId, String attendantId, String action) {
        Travel travel = this.travelRepository.findById(travelId).orElse(null);
        if(travel != null) {
            switch (action.toLowerCase()) {
                case "add":
                    User user = this.userRepository.findById(attendantId).orElse(null);
                    if(user == null)
                        throw new EntityNotFoundException("There is no user with id: " + attendantId);
                    else
                        travel.addAttendant(user);
                    break;
                case "remove":
                    travel.removeAttendant(attendantId);
                    break;
                default:
                    throw new IllegalArgumentException("Not supported attendant action: " + action);
            }
            this.travelRepository.saveAndFlush(travel);
        } else {
            throw new EntityNotFoundException("There is no travel with id: " + travelId);
        }
    }
}
