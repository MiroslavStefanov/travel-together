package org.softuni.traveltogether.repositories;

import org.softuni.traveltogether.domain.entities.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, String> {
    Destination findFirstByName(String name);
}
