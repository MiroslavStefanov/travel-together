package org.softuni.traveltogether.repositories;

import org.softuni.traveltogether.domain.entities.TravelRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "travelRequests")
public interface TravelRequestRepository extends JpaRepository<TravelRequest, String> {

}
