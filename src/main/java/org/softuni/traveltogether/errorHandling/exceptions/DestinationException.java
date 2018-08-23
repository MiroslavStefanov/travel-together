package org.softuni.traveltogether.errorHandling.exceptions;

public class DestinationException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Invalid destination";

    public DestinationException() {
        super(DEFAULT_MESSAGE);
    }

    public DestinationException(String message) {
        super(message);
    }

    public DestinationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DestinationException(Throwable cause) {
        super(cause);
    }
}
