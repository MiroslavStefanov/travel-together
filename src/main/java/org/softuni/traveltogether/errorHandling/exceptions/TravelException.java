package org.softuni.traveltogether.errorHandling.exceptions;

public class TravelException extends RuntimeException {
    private static final String DEFAULT_TRAVEL_EXCPETION_MESSAGE = "Invalid travel";

    public TravelException() {
        super(DEFAULT_TRAVEL_EXCPETION_MESSAGE);
    }

    public TravelException(String message) {
        super(message);
    }

    public TravelException(String message, Throwable cause) {
        super(message, cause);
    }

    public TravelException(Throwable cause) {
        super(cause);
    }
}
