package org.softuni.traveltogether.domain.projections;

import org.softuni.traveltogether.domain.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "userAdminProjection", types = {User.class})
public interface UserAdminProjection {
    String getUsername();

    @Value("#{target.role.name().split('_')[1]}")
    String getRole();
}
