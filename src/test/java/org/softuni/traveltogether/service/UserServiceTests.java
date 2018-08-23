package org.softuni.traveltogether.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.traveltogether.config.ModelMapperConfiguration;
import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.domain.models.service.UserServiceModel;
import org.softuni.traveltogether.errorHandling.exceptions.UserException;
import org.softuni.traveltogether.repositories.RoleRepository;
import org.softuni.traveltogether.repositories.UserRepository;
import org.softuni.traveltogether.services.CloudService;
import org.softuni.traveltogether.services.UserService;
import org.softuni.traveltogether.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.io.IOException;

import static junit.framework.TestCase.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@Import({ModelMapperConfiguration.class, LocalValidatorFactoryBean.class, SessionRegistryImpl.class, UserServiceImpl.class})
@ActiveProfiles("test")
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private CloudService cloudService;

    @Autowired
    private UserService userService;

    @Before
    public void setUpMocks() throws IOException {
        given(this.bCryptPasswordEncoder.encode(anyString())).willReturn("1234");
        given(this.cloudService.deleteImage(anyString())).willReturn(true);
        given(this.cloudService.uploadImage(any(), anyString())).willReturn("/img");
    }

    @Test
    public void testSaveUser_newUserServiceModel_expectAnyId() {
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("username");
        userServiceModel.setPassword("");

        String userId = this.userService.saveUser(userServiceModel);

        assertNotNull("Saving new user does not work", userId);
    }

    @Test
    public void testSaveUser_editedUserServiceModel_expectTheEmail() {
        String email = "abv";

        User user = new User();
        user.setUsername("username");
        user.setPassword("1234");
        user = this.userRepository.save(user);

        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        userServiceModel.setEmail(email);

        String userId = this.userService.saveUser(userServiceModel);

        assertEquals("Saving edited user does not work", email, this.userRepository.findById(userId).get().getEmail());
    }

    @Test
    public void testSaveTravel_invalidTravelServiceModel_expectException() {
        UserServiceModel userServiceModel = new UserServiceModel();

        try {
            this.userService.saveUser(userServiceModel);
        } catch (UserException e){
            System.out.println(e.getMessage());
            return;
        }

        fail("Invalid service model does not throw exception");
    }

    @Test
    public void testSaveTravel_travelServiceModelWithDuplicatedUsernameAndEmail_expectExceptions() {
        String username = "username";
        String email = "email";

        User user = new User();
        user.setUsername(username);
        user.setPassword("1234");
        user = this.userRepository.save(user);

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername(username);
        userServiceModel.setPassword("");

        try {
            this.userService.saveUser(userServiceModel);
        } catch (UserException e){
            System.out.println(e.getMessage());
            userServiceModel.setUsername("12345");
            userServiceModel.setEmail(email);
            try {
                this.userService.saveUser(userServiceModel);
            } catch (UserException ex){
                System.out.println(ex.getMessage());
                return;
            }
        }

        fail("Invalid service model does not throw exception");
    }
}
