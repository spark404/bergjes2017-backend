package net.strocamp.bergjes.exceptions;

/**
 * Created by hugo on 26/04/2017.
 */
public class LocationMismatchException extends RuntimeException {
    public LocationMismatchException() {
        super();
    }

    public LocationMismatchException(String message) {
        super(message);
    }

    public LocationMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocationMismatchException(Throwable cause) {
        super(cause);
    }
}
