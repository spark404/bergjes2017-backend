package net.strocamp.bergjes.exceptions;

/**
 * Created by hugo on 17/04/2017.
 */
public class QuestionAlreadyAnsweredException extends RuntimeException {
    public QuestionAlreadyAnsweredException() {
        super();
    }

    public QuestionAlreadyAnsweredException(String message) {
        super(message);
    }

    public QuestionAlreadyAnsweredException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuestionAlreadyAnsweredException(Throwable cause) {
        super(cause);
    }
}
