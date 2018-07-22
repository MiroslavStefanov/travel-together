package org.softuni.traveltogether.services;

import org.softuni.traveltogether.specific.UserRole;

public interface RoleService {
    UserRole getRole(String roleName);
}
