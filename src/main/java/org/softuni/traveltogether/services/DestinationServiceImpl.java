package org.softuni.traveltogether.services;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.entities.Destination;
import org.softuni.traveltogether.domain.models.service.DestinationServiceModel;
import org.softuni.traveltogether.errorHandling.exceptions.DestinationException;
import org.softuni.traveltogether.errorHandling.exceptions.EntityNotFoundException;
import org.softuni.traveltogether.repositories.DestinationRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
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
    @CacheEvict(cacheNames = {DESTINATION_NAMES_CACHE}, allEntries = true)
    public String saveDestination(DestinationServiceModel destinationServiceModel) {
        Optional<Destination> destinationCadnidate;
        Destination destination;
        if(destinationServiceModel.getId() != null
                && (destinationCadnidate = this.destinationRepository.findById(destinationServiceModel.getId())).isPresent()) {
            destination = destinationCadnidate.get();
            this.modelMapper.map(destinationServiceModel, destination);
        } else {
            destination = this.modelMapper.map(destinationServiceModel, Destination.class);
        }

        Set<ConstraintViolation<Destination>> errors = this.validator.validate(destination);
        if(!errors.isEmpty()) {
            StringBuilder messageBuilder = new StringBuilder("Invalid properties of destination ")
                    .append(errors.stream().findFirst().get().getRootBean())
                    .append("\r\n");
            for (ConstraintViolation<Destination> error : errors) {
                messageBuilder
                        .append("\t")
                        .append(error.getPropertyPath().toString())
                        .append(": ")
                        .append(error.getInvalidValue()).append("\r\n");
            }

            throw new DestinationException(messageBuilder.toString());
        }

        try{
            destination = this.destinationRepository.saveAndFlush(destination);
            return destination.getId();
        } catch (Exception e) {
            throw new DestinationException("Saving travel failed.", e);
        }
    }

    @Override
    @CacheEvict(cacheNames = {DESTINATION_NAMES_CACHE}, allEntries = true)
    public void deleteDestination(String id) {
        this.destinationRepository.deleteById(id);
    }

    @Override
    public DestinationServiceModel getDestination(String id) {
        return this.destinationRepository
                .findById(id)
                .map(t -> this.modelMapper.map(t, DestinationServiceModel.class))
                .orElseThrow(() -> new EntityNotFoundException("There is no destination with id: " + id));
    }

    @Override
    public DestinationServiceModel findDestinationByName(String name) {
        return this.modelMapper.map(this.destinationRepository.findFirstByName(name), DestinationServiceModel.class);
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

    @Override
    public List<DestinationServiceModel> getAllWithMinTravels(long minTravels) {
        return this.destinationRepository.findAll()
                .stream()
                .filter(d -> d.getTravels().size() >= minTravels)
                .map(d -> this.modelMapper.map(d, DestinationServiceModel.class))
                .collect(Collectors.toList());
    }
}
