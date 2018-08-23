package org.softuni.traveltogether.errorHandling.exceptions;

import org.softuni.traveltogether.domain.models.service.UserServiceModel;

public class UserException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Invalid user";

    protected UserServiceModel serviceModel;

    public UserException() {
        super(DEFAULT_MESSAGE);
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    public UserException(String message, UserServiceModel serviceModel) {
        super(message);
        this.serviceModel = serviceModel;
    }

    public UserServiceModel getServiceModel() {
        return serviceModel;
    }
}
