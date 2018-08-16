package org.softuni.traveltogether.specific;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.domain.models.service.TravelRequestServiceModel;
import org.softuni.traveltogether.domain.models.service.UserServiceModel;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserRequestManagerTests {
    private static final String USERNAME = "username";
    private static final String REQUEST_ID = "requestId";
    private static final String ANOTHER_REQUEST_ID = "anotherRequestId";

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserRequestManager manager;


    private TravelRequestServiceModel getRequest(String requestId) {
        UserServiceModel user = new UserServiceModel();
        user.setUsername(USERNAME);
        TravelRequestServiceModel request = new TravelRequestServiceModel();
        request.setId(requestId);
        request.setUser(user);
        return request;
    }

    private Map<String, Set<TravelRequestServiceModel>> getRequestMap() {
        Object field = ReflectionTestUtils.getField(this.manager, "requestMap");
        return (Map<String, Set<TravelRequestServiceModel>>) field;
    }

    private void testMap(String method) {
        Map<String, Set<TravelRequestServiceModel>> map = this.getRequestMap();
        assertTrue("UserRequestManager -> " + method + " does not work: Empty map", map.size() > 0);
        assertTrue("UserRequestManager -> " + method + " does not work: Key absent", map.containsKey(USERNAME));
        assertTrue("UserRequestManager -> " + method + " does not work: Empty request set", map.get(USERNAME).size() > 0);

    }

    @Test
    public void testAddUser_userWithOneRequest_expectUsernameWithRequestId() {
        Set<TravelRequestServiceModel> requests = new HashSet<>(){{
            add(getRequest(REQUEST_ID));
        }};

        this.manager.addUser(USERNAME, requests);

        this.testMap("addUser");
        assertEquals("UserRequestManager -> addUser does not work: Wrong request id",
                REQUEST_ID, ((TravelRequestServiceModel)(this.getRequestMap().get(USERNAME).toArray()[0])).getId());
    }

    @Test
    public void testAddRequest_userWithOneRequest_expectUsernameWithRequestId() {
        this.manager.addUser(USERNAME, new HashSet<>());

        this.manager.addRequest(this.getRequest(REQUEST_ID));

        this.testMap("addRequest");
        assertEquals("UserRequestManager -> addRequest does not work: Wrong request id",
                REQUEST_ID, ((TravelRequestServiceModel)(this.getRequestMap().get(USERNAME).toArray()[0])).getId());
    }

    @Test
    public void testGetRequestCount_userWithTwoRequest_expectEqualCountOfRequests() {
        Set<TravelRequestServiceModel> requests = new HashSet<>() {{
            add(getRequest(REQUEST_ID));
            add(getRequest(ANOTHER_REQUEST_ID));
        }};

        this.manager.addUser(USERNAME, requests);

        this.testMap("getRequestCount");
        assertEquals("UserRequestManager -> getRequestCount does not work: Wrong set size", requests.size(), (int)this.manager.getRequestCount(USERNAME));
    }

    @Test
    public void testGetRequests_userWithOneRequest_expectIdOfRequest() {
        Set<TravelRequestServiceModel> requests = new HashSet<>(){{
            add(getRequest(REQUEST_ID));
        }};

        this.manager.addUser(USERNAME, requests);

        this.testMap("getRequests");
        assertEquals("UserRequestManager -> getRequests does not work: Wrong request id",
                REQUEST_ID, ((TravelRequestServiceModel)(this.manager.getRequests(USERNAME).toArray()[0])).getId());
    }

    @Test
    public void testRemoveRequest_userWithOneRequest_expectEmptySetOfRequests() {
        Set<TravelRequestServiceModel> requests = new HashSet<>(){{
            add(getRequest(REQUEST_ID));
        }};
        this.manager.addUser(USERNAME, requests);

        this.manager.removeRequest(this.getRequest(REQUEST_ID));

        Map<String, Set<TravelRequestServiceModel>> map = this.getRequestMap();
        assertTrue("UserRequestManager -> removeRequest: does not work: Empty map", map.size() > 0);
        assertTrue("UserRequestManager -> removeRequest: does not work: Key absent", map.containsKey(USERNAME));
        assertEquals("UserRequestManager -> removeRequest: does not work: Request not deleted", 0, map.get(USERNAME).size());
    }
}
