package org.softuni.traveltogether.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.config.ModelMapperConfiguration;
import org.softuni.traveltogether.domain.entities.Destination;
import org.softuni.traveltogether.domain.entities.Travel;
import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.domain.models.service.DestinationServiceModel;
import org.softuni.traveltogether.domain.models.service.TravelServiceModel;
import org.softuni.traveltogether.domain.models.view.UserLinkViewModel;
import org.softuni.traveltogether.errorHandling.exceptions.EntityNotFoundException;
import org.softuni.traveltogether.errorHandling.exceptions.TravelException;
import org.softuni.traveltogether.repositories.DestinationRepository;
import org.softuni.traveltogether.repositories.TravelRepository;
import org.softuni.traveltogether.repositories.UserRepository;
import org.softuni.traveltogether.services.TravelService;
import org.softuni.traveltogether.services.TravelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@Import({TravelServiceImpl.class, ModelMapperConfiguration.class, LocalValidatorFactoryBean.class})
@ActiveProfiles("test")
public class TravelServiceTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TravelService travelService;

    private User buildUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user = this.userRepository.save(user);
        return user;
    }

    private Destination buildDestination() {
        Destination destination = new Destination("Sofia", 0.0, 0.0);
        destination = this.destinationRepository.save(destination);
        return destination;
    }

    private Travel buildTravel(boolean addAttendants) {
        User user = this.buildUser();

        Destination destination = this.buildDestination();

        Travel travel = new Travel();
        travel.setPublishedAt(LocalDateTime.now());
        travel.setDepartureTime(LocalDateTime.now());
        travel.setFromDestination(destination);
        travel.setToDestination(destination);
        travel.setPublisher(user);
        if(addAttendants)
            travel.addAttendant(user);
        travel = this.travelRepository.save(travel);

        return travel;
    }

    @Test
    public void testManageAttendant_userIdAndTravelIdWithAddAction_expectTravelWithAttendantWithTheUserId() {
        Travel travel = this.buildTravel(false);

        this.travelService.manageAttendant(travel.getId(), travel.getPublisher().getId(),  "add");

        assertEquals("Adding attendants does not work", travel.getPublisher().getId(), ((User)(travel.getAttendants().toArray()[0])).getId());
    }

    @Test
    public void testManageAttendant_userIdAndTravelIdWithRemoveAction_expectTravelWithoutAttendants() {
        Travel travel = this.buildTravel(true);

        this.travelService.manageAttendant(travel.getId(), travel.getPublisher().getId(), "remove");

        assertEquals("Removing attendants does not work", 0, travel.getAttendants().size());
    }

    @Test
    public void testManageAttendant_invalidParameters_expectExceptions() {
        Travel travel = this.buildTravel(false);

        try{
            this.travelService.manageAttendant("", "", "");
        } catch (EntityNotFoundException e) {
            assertTrue("Invalid travel id does not throw exception", e.getMessage().startsWith("There is no travel with id:"));
        }

        try{
            this.travelService.manageAttendant(travel.getId(), "", "add");
        } catch (EntityNotFoundException e) {
            assertTrue("Invalid user id does not throw exception", e.getMessage().startsWith("There is no user with id:"));
        }

        try{
            this.travelService.manageAttendant(travel.getId(), travel.getPublisher().getId(), "");
        } catch (IllegalArgumentException e) {
            assertTrue("Invalid action does not throw exception", e.getMessage().startsWith("Not supported attendant action:"));
        }
    }

    @Test
    public void testSaveTravel_newTravelServiceModel_expectAnyId() {
        User user = this.buildUser();
        Destination destination = this.buildDestination();
        DestinationServiceModel destinationServiceModel = this.modelMapper.map(destination, DestinationServiceModel.class);
        TravelServiceModel travelServiceModel = new TravelServiceModel();
        travelServiceModel.setPublishedAt(LocalDateTime.now());
        travelServiceModel.setDepartureTime(LocalDateTime.now());
        travelServiceModel.setFromDestination(destinationServiceModel);
        travelServiceModel.setToDestination(destinationServiceModel);
        travelServiceModel.setPublisher(new UserLinkViewModel());
        travelServiceModel.getPublisher().setUsername(user.getUsername());

        String travelId = this.travelService.saveTravel(travelServiceModel);

        assertNotNull("Saving new travel does not work", travelId);
    }

    @Test
    public void testSaveTravel_editedTravelServiceModel_expectTheDescription() {
        Travel travel = this.buildTravel(false);
        TravelServiceModel travelServiceModel = this.modelMapper.map(travel, TravelServiceModel.class);
        String description = "...";
        travelServiceModel.setDescription(description);

        String travelId = this.travelService.saveTravel(travelServiceModel);

        assertEquals("Saving edited travel does not work", description, this.travelRepository.findById(travelId).get().getDescription());
    }

    @Test
    public void testSaveTravel_invalidTravelServiceModel_expectException() {
        TravelServiceModel travelServiceModel = new TravelServiceModel();
        travelServiceModel.setPublisher(new UserLinkViewModel());

        try {
            this.travelService.saveTravel(travelServiceModel);
        } catch (TravelException e){
            System.out.println(e.getMessage());
            return;
        }

        fail("Invalid service model does not throw exception");
    }
}
