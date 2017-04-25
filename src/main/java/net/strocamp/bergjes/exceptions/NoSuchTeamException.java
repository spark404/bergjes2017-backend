package net.strocamp.bergjes.exceptions;

/**
 * Created by hugo on 25/04/2017.
 */
public class NoSuchTeamException extends RuntimeException {
    public NoSuchTeamException() {
        super();
    }

    public NoSuchTeamException(String message) {
        super(message);
    }

    public NoSuchTeamException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchTeamException(Throwable cause) {
        super(cause);
    }
}
