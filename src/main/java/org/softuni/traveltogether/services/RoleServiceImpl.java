package org.softuni.traveltogether.services;

import org.softuni.traveltogether.domain.entities.Role;
import org.softuni.traveltogether.repositories.RoleRepository;
import org.softuni.traveltogether.specific.UserRole;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public UserRole getRole(String roleName) {
        Role role = this.roleRepository.findFirstByAuthority(roleName);
        if(role == null)
            return UserRole.INVALID_ROLE;
        return UserRole.valueOf(role.getAuthority());
    }
}
