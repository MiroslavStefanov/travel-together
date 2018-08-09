package org.softuni.traveltogether.services;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.entities.Destination;
import org.softuni.traveltogether.domain.entities.Travel;
import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.domain.models.binding.TravelCreateBindingModel;
import org.softuni.traveltogether.domain.models.service.TravelServiceModel;
import org.softuni.traveltogether.repositories.DestinationRepository;
import org.softuni.traveltogether.repositories.TravelRepository;
import org.softuni.traveltogether.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TravelServiceImpl implements TravelService {
    private final TravelRepository travelRepository;
    private final UserRepository userRepository;
    private final DestinationRepository destinationRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public TravelServiceImpl(TravelRepository travelRepository, UserRepository userRepository, DestinationRepository destinationRepository, ModelMapper modelMapper, Validator validator) {
        this.travelRepository = travelRepository;
        this.userRepository = userRepository;
        this.destinationRepository = destinationRepository;
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
            String debug = SecurityContextHolder.getContext().getAuthentication().getName();
            User currentUser = this.userRepository.findFirstByUsername(debug);
            Destination fromDest = this.destinationRepository.findFirstByName(travelCreateBindingModel.getFrom());
            Destination toDest = this.destinationRepository.findFirstByName(travelCreateBindingModel.getTo());
            travel.setPublisher(currentUser);
            travel.setPublishedAt(LocalDateTime.now());
            travel.setFromDestination(fromDest);
            travel.setToDestination(toDest);
            travel = this.travelRepository.save(travel);
            return travel.getId();
        } catch (Exception e) {
            //better handling of errors
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<TravelServiceModel> get5MostRecent() {
        return this.travelRepository.findTop5ByOrderByPublishedAtDesc()
                .stream()
                .map(t -> this.modelMapper.map(t, TravelServiceModel.class))
                .collect(Collectors.toList());
    }
}
