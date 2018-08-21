package org.softuni.traveltogether.specific;

import org.softuni.traveltogether.common.io.FileIO;
import org.softuni.traveltogether.domain.entities.Destination;
import org.softuni.traveltogether.domain.entities.Role;
import org.softuni.traveltogether.domain.entities.Travel;
import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.repositories.DestinationRepository;
import org.softuni.traveltogether.repositories.RoleRepository;
import org.softuni.traveltogether.repositories.TravelRepository;
import org.softuni.traveltogether.repositories.UserRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class DatabaseSeeder {
    private static final String ROOT_USER_USERNAME = "root";
    private static final String ROOT_USER_PASSWORD = "rootPsw1234";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DestinationRepository destinationRepository;
    private final TravelRepository travelRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final FileIO fileService;

    public DatabaseSeeder(UserRepository userRepository, RoleRepository roleRepository, DestinationRepository destinationRepository, TravelRepository travelRepository, BCryptPasswordEncoder bCryptPasswordEncoder, FileIO fileService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.destinationRepository = destinationRepository;
        this.travelRepository = travelRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.fileService = fileService;
    }

    @EventListener
    public void handleContextRefreshListener(ContextRefreshedEvent event) throws IOException {
        this.seedRoles();
        this.seedRootUser();
        this.seedDestinations();
        //this.seedTravels();
    }

    private void seedRoles() {
        if(this.roleRepository.count() == 0) {
            List<Role> roles = Arrays.stream(UserRole.values())
                    .filter(r -> !r.equals(UserRole.ROLE_INVALID))
                    .map(role->new Role(role.toString()))
                    .collect(Collectors.toList());

            roles = this.roleRepository.saveAll(roles);
        }
    }

    private void seedRootUser(){
        if(this.userRepository.count() == 0) {
            User user = new User();
            user.setUsername(ROOT_USER_USERNAME);
            user.setPassword(this.bCryptPasswordEncoder.encode(ROOT_USER_PASSWORD));
            user.setFirstName("Root");
            user.setLastName("Rootov");
            Set<Role> rootRoleSet = new HashSet<>(){{
                add(roleRepository.findFirstByAuthority(UserRole.ROLE_ADMIN.toString()));
                add(roleRepository.findFirstByAuthority(UserRole.ROLE_ROOT.toString()));
            }};
            user.setAuthorities(rootRoleSet);
            this.userRepository.saveAndFlush(user);
        }
    }

    private void seedDestinations() throws IOException {
        if(this.destinationRepository.count() == 0) {
            String destContent = this.fileService.read("/db/cities");
            this.destinationRepository.saveAll(
                    Arrays.stream(destContent.split("\\r\\n"))
                            .map(line -> line.split(","))
                            .map(data -> new Destination(data[1]+", "+data[5], Double.parseDouble(data[2]), Double.parseDouble(data[3])))
                            .collect(Collectors.toSet()));
        }
    }

    private void seedTravels() {
        if(travelRepository.count() == 0) {
            List<User> allUsers = this.userRepository.findAll();
            List<Destination> allDestinations = this.destinationRepository.findAll();
            Random random = new Random();
            allUsers.forEach(u -> {
                Set<Travel> travels = new HashSet<>();
                for (int i = 0; i < 300; i++) {
                    Travel travel = new Travel();
                    travel.setPublisher(u);
                    travel.setPublishedAt(LocalDateTime.now().minusDays(i));
                    travel.setDepartureTime(LocalDateTime.now().plusDays(i));
                    travel.setFromDestination(allDestinations.get(random.nextInt(allDestinations.size() - 1)));
                    travel.setToDestination(allDestinations.get(random.nextInt(allDestinations.size() - 1)));
                    travel.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur convallis gravida dui, at tempor libero ultrices eu. Fusce lobortis odio nulla, volutpat auctor urna accumsan et. Suspendisse ullamcorper nunc non magna ultrices, ac maximus nibh facilisis. Mauris ut nisi nec sem mattis tempor. In eget diam tortor. Cras sodales tempus ex elementum ullamcorper. Nam blandit diam neque, id consequat nisi egestas sit amet. Vivamus tincidunt vestibulum lorem, ut semper eros. Praesent sed fringilla nisi, a efficitur lectus. Nulla scelerisque massa risus, vitae commodo sapien rutrum tincidunt. Integer sit amet mauris velit. Praesent scelerisque magna vel laoreet pulvinar. Phasellus suscipit venenatis diam nec condimentum. In eget dolor ac justo lobortis dignissim. Nulla dignissim efficitur neque ac convallis.");
                    travels.add(travel);
                }
                this.travelRepository.saveAll(travels);
            });
        }
    }
}
