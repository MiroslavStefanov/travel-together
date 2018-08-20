package org.softuni.traveltogether.services;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.entities.Destination;
import org.softuni.traveltogether.domain.models.service.DestinationServiceModel;
import org.softuni.traveltogether.repositories.DestinationRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class DestinationServiceImpl implements DestinationService {
    private static final String DESTINATION_NAMES_CACHE = "destinationNames";

    private final DestinationRepository destinationRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public DestinationServiceImpl(DestinationRepository destinationRepository, ModelMapper modelMapper, Validator validator) {
        this.destinationRepository = destinationRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    @CacheEvict(DESTINATION_NAMES_CACHE)
    public void saveDestination(DestinationServiceModel destinationServiceModel) {
        Destination destination = this.modelMapper.map(destinationServiceModel, Destination.class);

        Set<ConstraintViolation<Destination>> errors = this.validator.validate(destination);
        if(!errors.isEmpty()) {
            //send errors data
            //throw exception
        }

        try{
            destination = this.destinationRepository.saveAndFlush(destination);
        } catch (Exception e) {
            //better handling of errors
            e.printStackTrace();
        }
    }

    @Override
    @Cacheable(DESTINATION_NAMES_CACHE)
    public List<String> getAllDestinationsNames() {
        return this.destinationRepository.findAll()
                .stream()
                .map(Destination::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
