package net.strocamp.bergjes.exceptions;

/**
 * Created by hugo on 15/04/2017.
 */
public class NoSuchLocationException extends RuntimeException {
    public NoSuchLocationException() {
        super();
    }

    public NoSuchLocationException(String message) {
        super(message);
    }

    public NoSuchLocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchLocationException(Throwable cause) {
        super(cause);
    }
}
