package org.softuni.traveltogether.modelMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.config.ModelMapperConfiguration;
import org.softuni.traveltogether.domain.entities.Comment;
import org.softuni.traveltogether.domain.entities.Destination;
import org.softuni.traveltogether.domain.entities.Travel;
import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.domain.models.binding.TravelCreateBindingModel;
import org.softuni.traveltogether.domain.models.service.CommentServiceModel;
import org.softuni.traveltogether.domain.models.service.DestinationServiceModel;
import org.softuni.traveltogether.domain.models.service.TravelServiceModel;
import org.softuni.traveltogether.domain.models.service.UserServiceModel;
import org.softuni.traveltogether.domain.models.view.CommentViewModel;
import org.softuni.traveltogether.domain.models.view.TravelDetailsViewModel;
import org.softuni.traveltogether.domain.models.view.TravelLinkViewModel;
import org.softuni.traveltogether.domain.models.view.UserLinkViewModel;
import org.softuni.traveltogether.repositories.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ModelMapperTests {

    @Mock
    private DestinationRepository destinationRepository;

    @InjectMocks
    private ModelMapperConfiguration configuration;

    @Test
    public void testTravelServiceMapping_travel_expectTravelServiceModelWithMappedCollectionsOfObjects() {
        String userId = "test-user-uuid";
        String commentContent = "test-comment-content";

        User user = new User();
        user.setId(userId);

        Comment comment = new Comment();
        comment.setContent(commentContent);

        Travel travel = new Travel();
        travel.setAttendants(new HashSet<>(){{
            add(user);
        }});
        travel.setComments(new ArrayList<>(){{
            add(comment);
        }});

        TravelServiceModel tsm = this.configuration.getModelMapper().map(travel, TravelServiceModel.class);

        assertEquals("User was not mapped", userId, ((UserLinkViewModel)(tsm.getAttendants().toArray()[0])).getId());
        assertEquals("Comment was not mapped", commentContent, tsm.getComments().get(0).getContent());
    }

    @Test
    public void testTravelDetailsVewMapping_travelServiceModel_expectTravelDetailsVewModelWithMappedCollectionsOfObjects() {
        String userId = "test-user-uuid";
        String commentContent = "test-comment-content";

        UserLinkViewModel user = new UserLinkViewModel();
        user.setId(userId);

        CommentServiceModel comment = new CommentServiceModel();
        comment.setContent(commentContent);

        TravelServiceModel travel = new TravelServiceModel();
        travel.setAttendants(new HashSet<>() {{
            add(user);
        }});
        travel.setComments(new ArrayList<>(){{
            add(comment);
        }});

        TravelDetailsViewModel tsm = this.configuration.getModelMapper().map(travel, TravelDetailsViewModel.class);

        assertEquals("User was not mapped", userId, ((UserLinkViewModel) tsm.getAttendants().toArray()[1]).getId());
        assertEquals("Comment was not mapped", commentContent, tsm.getComments().get(0).getContent());
    }

    @Test
    public void testConvertingSetOfTravelToSetOfTravelServiceModel_userWithSetsOfTravels_expectUserServiceModelWithMappedSetsOfTravelServiceModels() {
        String publishedId = "published-id";
        String attendedId = "attended-travel-id";

        Travel publishedTravel = new Travel();
        publishedTravel.setId(publishedId);
        publishedTravel.setDepartureTime(LocalDateTime.now().plusDays(2));

        Travel attendedTravel = new Travel();
        attendedTravel.setId(attendedId);
        attendedTravel.setDepartureTime(LocalDateTime.now().plusDays(2));

        User user = new User();
        user.setTravels(new HashSet<>() {{
            add(publishedTravel);
        }});
        user.setAttendedTravels(new HashSet<>() {{
            add(attendedTravel);
        }});

        UserServiceModel usm = this.configuration.getModelMapper().map(user, UserServiceModel.class);

        assertEquals(
                "Converting collection of Travel to collections of TravelServiceModel does not work",
                publishedId+";"+attendedId,
                ((TravelLinkViewModel)(usm.getTravels().toArray()[0])).getId() + ";"
                + ((TravelLinkViewModel)(usm.getAttendedTravels().toArray()[0])).getId()
                );
    }

    @Test
    public void testTravelLnkViewMapping_travel_expectTravelLinkViewModelWithMappedName() {
        String travelId = "travel-uuid";

        Destination from = new Destination();
        from.setName("Sofia");

        Destination to = new Destination();
        to.setName("Burgas");

        Travel travel = new Travel();
        travel.setId(travelId);
        travel.setFromDestination(from);
        travel.setToDestination(to);

        TravelLinkViewModel tlvm = this.configuration.getModelMapper().map(travel, TravelLinkViewModel.class);

        assertEquals("TravelLinkMapping does not map name", to.getName(), tlvm.getName());
    }

    @Test
    public void testTravelCreateBindingMapping_travelCreateBindingModelWithFromDestination_expectFromDestinationName() {
        String name = "Sofia";
        Destination sofia = new Destination();
        sofia.setName(name);
        LocalDateTime now = LocalDateTime.now();

        when(this.destinationRepository.findFirstByName(anyString()))
                .thenReturn(sofia);

        TravelCreateBindingModel travelModel = new TravelCreateBindingModel();
        travelModel.setFrom(name);
        travelModel.setTo(name);

        Travel travel = this.configuration.getModelMapper().map(travelModel, Travel.class);

        assertEquals("travelCreateBindingMapping does not map destinations", name, travel.getFromDestination().getName());
    }
}
