package org.softuni.traveltogether.services;

import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.entities.Destination;
import org.softuni.traveltogether.repositories.DestinationRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DestinationServiceImpl implements DestinationService {
    private final DestinationRepository destinationRepository;
    private final ModelMapper modelMapper;

    public DestinationServiceImpl(DestinationRepository destinationRepository, ModelMapper modelMapper) {
        this.destinationRepository = destinationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<String> getAllDestinationsNames() {
        return this.destinationRepository.findAll()
                .stream()
                .map(Destination::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
