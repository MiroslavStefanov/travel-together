package org.softuni.traveltogether.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.traveltogether.domain.entities.Destination;
import org.softuni.traveltogether.domain.entities.Travel;
import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.repositories.DestinationRepository;
import org.softuni.traveltogether.repositories.TravelRepository;
import org.softuni.traveltogether.repositories.UserRepository;
import org.softuni.traveltogether.services.TravelService;
import org.softuni.traveltogether.services.TravelServiceImpl;
import org.softuni.traveltogether.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@ActiveProfiles("test")
public class TravelServiceTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private TravelService travelService;

    @Test
    public void testAddAttendant_userIdAndTravelId_expectTravelWithAttendantWithTheUserId() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user = this.userRepository.save(user);

        Destination destination = new Destination("Sofia", 0.0, 0.0);
        destination = this.destinationRepository.save(destination);

        Travel travel = new Travel();
        travel.setPublishedAt(LocalDateTime.now());
        travel.setDepartureTime(LocalDateTime.now());
        travel.setFromDestination(destination);
        travel.setToDestination(destination);
        travel.setPublisher(user);
        travel = this.travelRepository.save(travel);

        this.travelService.addAttendant(travel.getId(), user.getId());

        assertEquals("Kur", user.getId(), ((User)(travel.getAttendants().toArray()[0])).getId());
    }
}
