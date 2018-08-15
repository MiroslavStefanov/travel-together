package org.softuni.traveltogether.services;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.entities.Destination;
import org.softuni.traveltogether.domain.entities.Travel;
import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.domain.models.binding.TravelCreateBindingModel;
import org.softuni.traveltogether.domain.models.service.TravelServiceModel;
import org.softuni.traveltogether.domain.models.service.UserServiceModel;
import org.softuni.traveltogether.domain.models.view.UserLinkViewModel;
import org.softuni.traveltogether.repositories.DestinationRepository;
import org.softuni.traveltogether.repositories.TravelRepository;
import org.softuni.traveltogether.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public String saveTravel(TravelCreateBindingModel travelCreateBindingModel) {
        Set<ConstraintViolation<TravelCreateBindingModel>> errors = this.validator.validate(travelCreateBindingModel);
        if(!errors.isEmpty()) {
            //send errors data
            //throw exception
        }

        try{
            Travel travel = this.modelMapper.map(travelCreateBindingModel, Travel.class);
            User currentUser = this.userRepository.findFirstByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            travel.setPublisher(currentUser);
            travel.setPublishedAt(LocalDateTime.now());
            travel = this.travelRepository.saveAndFlush(travel);
            return travel.getId();
        } catch (Exception e) {
            //better handling of errors
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String editTravel(String travelId, TravelCreateBindingModel travelCreateBindingModel) {
        Set<ConstraintViolation<TravelCreateBindingModel>> errors = this.validator.validate(travelCreateBindingModel);
        if(!errors.isEmpty()) {
            //send errors data
            //throw exception
        }

        try{
            Travel travel = this.travelRepository.findById(travelId).orElse(null);
            this.modelMapper.map(travelCreateBindingModel, travel);
            travel = this.travelRepository.saveAndFlush(travel);
            return travel.getId();
        } catch (Exception e) {
            //better handling of errors
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteTravel(String travelId) {
        this.travelRepository.deleteById(travelId);
        this.travelRepository.flush();
    }

    @Override
    public TravelServiceModel getTravel(String id) {
        return this.travelRepository.findById(id).map(t -> this.modelMapper.map(t, TravelServiceModel.class)).orElse(null);
    }
}
