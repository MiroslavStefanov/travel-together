package org.softuni.traveltogether.repositories;

import org.softuni.traveltogether.domain.entities.User;
import org.softuni.traveltogether.domain.projections.UserAdminProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(excerptProjection = UserAdminProjection.class)
public interface UserRepository extends JpaRepository<User, String> {
    @RestResource(path = "/findUser")
    User findFirstByUsername(@Param("username") String username);
}
