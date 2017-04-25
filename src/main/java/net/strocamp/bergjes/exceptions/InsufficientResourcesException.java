package net.strocamp.bergjes.exceptions;

/**
 * Created by hugo on 25/04/2017.
 */
public class InsufficientResourcesException extends RuntimeException {
    public InsufficientResourcesException() {
        super();
    }

    public InsufficientResourcesException(String message) {
        super(message);
    }

    public InsufficientResourcesException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientResourcesException(Throwable cause) {
        super(cause);
    }
}
