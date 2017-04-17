package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.strocamp.bergjes.db.Question;
import net.strocamp.bergjes.domain.answer.Answer;
import net.strocamp.bergjes.domain.answer.AnswerResponse;
import net.strocamp.bergjes.domain.answer.AnswerStatus;
import net.strocamp.bergjes.domain.answer.AnswerType;
import net.strocamp.bergjes.exceptions.QuestionAlreadyAnsweredException;
import net.strocamp.bergjes.exceptions.QuestionNotUnlockedException;

import java.util.Map;

/**
 * Created by hugo on 11/04/2017.
 */
public class ScannedAnswerRequestHandler extends AbstractRequestHandler implements RequestHandler<Answer, AnswerResponse> {

    public AnswerResponse handleRequest(Answer answer, Context context) {
        init(context);

        AnswerResponse answerResponse = new AnswerResponse();

        if (!teamStatus.getQuestions().containsKey(answer.getQuestionKey())) {
            throw new QuestionNotUnlockedException("Deze vraag kan je nog niet beantwoorden, " +
                    "scan eerst de locatie die bij deze vraag hoort");
        }

        Map<String, String> currentQuestion = teamStatus.getQuestions().get(answer.getQuestionKey());
        AnswerStatus answerStatus = AnswerStatus.valueOf(currentQuestion.get("status"));
        if (!AnswerStatus.OPEN.equals(answerStatus)) {
            throw new QuestionAlreadyAnsweredException("Je kan maar 1 keer een antwoord geven op een vraag");
        }

        Question question = database.getQuestionbyKey(answer.getQuestionKey());
        if (AnswerType.valueOf(question.getCorrect()).equals(answer.getAnswer())) {
            answerResponse.setAnswerStatus(AnswerStatus.CORRECT);
        } else {
            answerResponse.setAnswerStatus(AnswerStatus.WRONG);
        }

        // Update the status of the answer
        currentQuestion.put("status", answerResponse.getAnswerStatus().name());

        // Update the resources
        if (AnswerStatus.CORRECT.equals(answerResponse.getAnswerStatus())) {
            Integer currentResourceAmount = teamStatus.getResources().get(currentQuestion.get("resource"));
            Integer newResourceAmount = currentResourceAmount + Integer.parseInt(currentQuestion.get("amount"));
            teamStatus.getResources().put(currentQuestion.get("resource"), newResourceAmount);
        }

        database.updateTeamStatus(teamStatus);
        return answerResponse;
    }
}
