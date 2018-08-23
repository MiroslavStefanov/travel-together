package org.softuni.traveltogether.errorHandling.exceptions;

import org.softuni.traveltogether.domain.models.service.UserServiceModel;

public class UsernameAlreadyUsedException extends UserException {
    private static final String DEFAULT_MESSAGE = "Username already used";

    public UsernameAlreadyUsedException() {
        super(DEFAULT_MESSAGE);
    }

    public UsernameAlreadyUsedException(UserServiceModel serviceModel) {
        super(DEFAULT_MESSAGE, serviceModel);
    }
}
