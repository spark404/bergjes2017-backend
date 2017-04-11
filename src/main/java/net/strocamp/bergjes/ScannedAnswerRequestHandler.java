package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.strocamp.bergjes.domain.answer.Answer;
import net.strocamp.bergjes.domain.answer.AnswerResponse;

/**
 * Created by hugo on 11/04/2017.
 */
public class ScannedAnswerRequestHandler implements RequestHandler<Answer, AnswerResponse> {
    public AnswerResponse handleRequest(Answer answer, Context context) {
        return null;
    }
}
