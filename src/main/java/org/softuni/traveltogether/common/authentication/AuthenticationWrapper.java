package org.softuni.traveltogether.common.authentication;

import org.springframework.security.core.Authentication;

public interface AuthenticationWrapper {
    Authentication getAuthentication();
}
