package org.softuni.traveltogether.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.traveltogether.domain.entities.Destination;
import org.softuni.traveltogether.domain.entities.Travel;
import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.repositories.DestinationRepository;
import org.softuni.traveltogether.repositories.TravelRepository;
import org.softuni.traveltogether.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@ActiveProfiles("test")
public class TravelRepositoryTests {
    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    private User getUser() {
        User user = new User();
        user.setUsername("pesho");
        user.setPassword("123");
        return user;
    }

    @Test
    public void testFindAllActive_anActiveAndAnInactiveTravels_expectActiveTravelId() {
        User user = this.userRepository.save(this.getUser());
        Destination destination = this.destinationRepository.save(new Destination("Sofia", 0.0, 0.0));

        Travel active = new Travel();
        active.setPublishedAt(LocalDateTime.now());
        active.setDepartureTime(LocalDateTime.now().plusDays(2));
        active.setPublisher(user);
        active.setToDestination(destination);
        active = this.travelRepository.save(active);

        Travel inactive = new Travel();
        inactive.setPublishedAt(LocalDateTime.now());
        inactive.setDepartureTime(LocalDateTime.now().minusDays(1));
        inactive.setPublisher(user);
        inactive.setToDestination(destination);
        this.travelRepository.save(inactive);

        Page<Travel> allActive = this.travelRepository.findAllActive(new PageRequest(0, 1));

        assertTrue("Query \"findAllActive\" is wrong", allActive.getTotalElements() > 0);
        assertEquals("Query \"findAllActive\" is wrong", active.getId(), allActive.getContent().get(0).getId());
    }

    @Test
    public void testFindAllActive_travelWithDepartureTimeToday_expectItsId() {
        User user = this.userRepository.save(this.getUser());
        Destination destination = this.destinationRepository.save(new Destination("Sofia", 0.0, 0.0));

        Travel today = new Travel();
        today.setPublisher(user);
        today.setToDestination(destination);
        today.setPublishedAt(LocalDateTime.now());
        today.setDepartureTime(LocalDateTime.now().minusSeconds(1));
        today = this.travelRepository.save(today);

        Page<Travel> allActive = this.travelRepository.findAllActive(new PageRequest(0, 1));

        assertTrue("Query \"findAllActive\" does not return travels scheduled for past hours today", allActive.getTotalElements() > 0);
        assertEquals("Query \"findAllActive\" does not return travels scheduled for past hours today", today.getId(), allActive.getContent().get(0).getId());
    }

    @Test
    public void testFindAllActiveBySearchParams_endDestinationName_expectIdOfTravelWithEndDestinationWithTheName() {
        User user = user = this.userRepository.save(this.getUser());
        Destination sofia = this.destinationRepository.save(new Destination("Sofia", 0.0, 0.0));
        Destination burgas = this.destinationRepository.save(new Destination("Burgas", 0.0, 0.0));

        Travel toSofia = new Travel();
        toSofia.setFromDestination(burgas);
        toSofia.setPublisher(user);
        toSofia.setToDestination(sofia);
        toSofia.setPublishedAt(LocalDateTime.now());
        toSofia.setDepartureTime(LocalDateTime.now().minusSeconds(1));
        toSofia = this.travelRepository.save(toSofia);

        Travel toBurgas = new Travel();
        toBurgas.setFromDestination(sofia);
        toBurgas.setPublisher(user);
        toBurgas.setToDestination(burgas);
        toBurgas.setPublishedAt(LocalDateTime.now());
        toBurgas.setDepartureTime(LocalDateTime.now().minusSeconds(1));
        toBurgas = this.travelRepository.save(toBurgas);

        Page<Travel> allActive = this.travelRepository.findAllActiveBySearchParameters(
                null,
                sofia.getName(),
                null,
                null,
                new PageRequest(0, 1)
        );

        assertTrue("Query \"findAllActiveBySearchParams\" does not work with destination name parameter", allActive.getTotalElements() > 0);
        assertEquals("Query \"findAllActiveBySearchParams\" does not work with destination name parameter", toSofia.getId(), allActive.getContent().get(0).getId());
    }

    @Test
    public void testFindAllActiveBySearchParams_allParameters_expectIdOfTravelSpecifiedParameters() {
        User user = user = this.userRepository.save(this.getUser());
        Destination sofia = this.destinationRepository.save(new Destination("Sofia", 0.0, 0.0));
        Destination burgas = this.destinationRepository.save(new Destination("Burgas", 1.0, 1.0));
        LocalDateTime now = LocalDateTime.now();

        Travel toSofia = new Travel();
        toSofia.setPublisher(user);
        toSofia.setFromDestination(burgas);
        toSofia.setToDestination(sofia);
        toSofia.setPublishedAt(now);
        toSofia.setDepartureTime(now);
        toSofia = this.travelRepository.save(toSofia);

        Page<Travel> allActive = this.travelRepository.findAllActiveBySearchParameters(
                burgas.getName(),
                sofia.getName(),
                now,
                now,
                new PageRequest(0, 1)
        );

        assertTrue("Query \"findAllActiveBySearchParams\" does not work with all parameters", allActive.getTotalElements() > 0);
        assertEquals("Query \"findAllActiveBySearchParams\" does not work with all parameters", toSofia.getId(), allActive.getContent().get(0).getId());
    }
}
