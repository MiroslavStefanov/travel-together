package org.softuni.traveltogether.specific;

import org.softuni.traveltogether.domain.entities.Role;
import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.repositories.RoleRepository;
import org.softuni.traveltogether.repositories.UserRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class DatabaseSeeder {
    private static final String ROOT_USER_USERNAME = "root";
    private static final String ROOT_USER_PASSWORD = "rootPsw1234";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public DatabaseSeeder(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;

        this.roleRepository = roleRepository;
    }

    @EventListener
    public void handleContextRefreshListener(ContextRefreshedEvent event){
        this.seedRoles();
        this.seedRootUser();
    }

    private void seedRoles() {
        if(this.roleRepository.count() == 0) {
            List<Role> roles = Arrays.stream(UserRole.values())
                    .map(role->new Role(role.toString()))
                    .collect(Collectors.toList());

            roles = this.roleRepository.saveAll(roles);
        }
    }

    private void seedRootUser(){
        if(this.userRepository.count() == 0) {
            User user = new User();
            user.setUsername(ROOT_USER_USERNAME);
            user.setUsername(ROOT_USER_PASSWORD);
            Set<Role> rootRoleSet = new HashSet<>(){{
                add(roleRepository.findFirstByAuthority(UserRole.ROLE_ADMIN.toString()));
            }};
            user.setAuthorities(rootRoleSet);
            this.userRepository.save(user);
        }
    }
}
