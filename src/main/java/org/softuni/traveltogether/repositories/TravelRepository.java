package org.softuni.traveltogether.repositories;

import org.softuni.traveltogether.domain.entities.Travel;
import org.softuni.traveltogether.domain.projections.TravelProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource(path = "travel_api", excerptProjection = TravelProjection.class)
public interface TravelRepository extends JpaRepository<Travel, String> {

    List<Travel> findTop5ByOrderByPublishedAtDesc();

    @Query("from Travel t where t.departureTime >= current_date")
    Page<Travel> findAllActive(Pageable pageable);

    @Query("from Travel t " +
            "where t.departureTime >= current_date and " +
            "(:sDest is null or t.fromDestination.name = :sDest) and " +
            "(:eDest is null or t.toDestination.name = :eDest) and " +
            "(:fDate is null or t.departureTime >= :fDate) and " +
            "(:tDate is null or t.departureTime <= :tDate)")
    Page<Travel> findAllActiveBySearchParameters(
            @Param("sDest") String from,
            @Param("eDest") String to,
            @Param("fDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateFrom,
            @Param("tDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateTo,
            Pageable pageable
    );
}
