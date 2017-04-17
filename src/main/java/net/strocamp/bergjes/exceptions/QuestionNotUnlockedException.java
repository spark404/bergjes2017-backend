package net.strocamp.bergjes.exceptions;

/**
 * Created by hugo on 17/04/2017.
 */
public class QuestionNotUnlockedException extends RuntimeException {
    public QuestionNotUnlockedException() {
        super();
    }

    public QuestionNotUnlockedException(String message) {
        super(message);
    }

    public QuestionNotUnlockedException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuestionNotUnlockedException(Throwable cause) {
        super(cause);
    }
}
