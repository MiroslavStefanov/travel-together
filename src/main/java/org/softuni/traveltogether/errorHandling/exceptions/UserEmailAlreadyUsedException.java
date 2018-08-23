package org.softuni.traveltogether.errorHandling.exceptions;

import org.softuni.traveltogether.domain.models.service.UserServiceModel;

public class UserEmailAlreadyUsedException extends UserException {
    private static final String DEFAULT_MESSAGE = "Email already used";

    public UserEmailAlreadyUsedException() {
        super(DEFAULT_MESSAGE);
    }

    public UserEmailAlreadyUsedException(UserServiceModel serviceModel) {
        super(DEFAULT_MESSAGE, serviceModel);
    }
}
