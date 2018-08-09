package org.softuni.traveltogether.repositories;

import org.softuni.traveltogether.domain.entities.Travel;
import org.softuni.traveltogether.domain.projections.TravelProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "travel_api", excerptProjection = TravelProjection.class)
public interface TravelRepository extends JpaRepository<Travel, String> {

    List<Travel> findTop5ByOrderByPublishedAtDesc();

    Page<Travel> findAll(Pageable pageable);
}
