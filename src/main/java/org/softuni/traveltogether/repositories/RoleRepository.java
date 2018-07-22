package org.softuni.traveltogether.repositories;

import org.softuni.traveltogether.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findFirstByAuthority(String name);
}
