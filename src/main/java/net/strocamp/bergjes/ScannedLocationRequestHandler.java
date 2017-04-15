package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.strocamp.bergjes.db.TeamStatus;
import net.strocamp.bergjes.domain.answer.AnswerType;
import net.strocamp.bergjes.domain.location.Location;
import net.strocamp.bergjes.domain.location.LocationResponse;
import net.strocamp.bergjes.domain.question.Question;
import net.strocamp.bergjes.domain.resource.Resource;
import net.strocamp.bergjes.domain.resource.ResourceType;
import net.strocamp.bergjes.exceptions.NoSuchLocationException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hugo on 10/04/2017.
 */
public class ScannedLocationRequestHandler extends AbstractRequestHandler implements RequestHandler<Location, LocationResponse> {

    public LocationResponse handleRequest(Location scanData, Context context) {
        init(context);

        LambdaLogger logger = context.getLogger();
        String roundCode = settings.get("currentRound");

        LocationResponse locationResponse = new LocationResponse();
        if (teamStatus.getVisitedLocationsPerRound().containsKey(roundCode) &&
                teamStatus.getVisitedLocationsPerRound().get(roundCode).contains(scanData.getLocationCode())) {
            // Been here before

            locationResponse.setLocationAvailable(false);
        } else {
            locationResponse.setLocationAvailable(true);

            net.strocamp.bergjes.db.Location dbLocation = database.getLocationByKey(scanData.getLocationCode());
            if (dbLocation == null) {
                String message = String.format("[%s] No location %s in database",
                        context.getClientContext().getClient().getInstallationId(), scanData.getLocationCode());
                logger.log(message);
                throw new NoSuchLocationException(message);
            }
            String questionCode;
            switch (roundCode) {
                case "round1":
                    questionCode = dbLocation.getRoundOneQuestion();
                    break;
                case "round2":
                    questionCode = dbLocation.getRoundTwoQuestion();
                    break;
                case "round3":
                    questionCode = dbLocation.getRoundThreeQuestion();
                    break;
                default:
                    throw new IllegalStateException("No round " + roundCode);
            }

            locationResponse.setQuestion(database.getQuestionbyKey(questionCode));
            locationResponse.setResource(getResource());

            if (!teamStatus.getVisitedLocationsPerRound().containsKey(roundCode)) {
                teamStatus.getVisitedLocationsPerRound().put(roundCode, new ArrayList<>());
            }

            teamStatus.getVisitedLocationsPerRound().get(roundCode).add(scanData.getLocationCode());
            teamStatus.getQuestions().put(questionCode, "OPEN");
            database.updateTeamStatus(teamStatus);
        }

        return locationResponse;
    }

    private Resource getResource() {
        Resource resource = new Resource();
        resource.setResourceType(ResourceType.EGG);
        resource.setAmount(1);
        return resource;
    }

    private Question getQuestion() {
        Question question = new Question();
        question.setQuestion("Dit is een vraag waarmee we dingen aan je vragen");
        HashMap<AnswerType, String> answers = new HashMap<>();
        question.setAnswers(answers);
        answers.put(AnswerType.A, "Geen Idee");
        answers.put(AnswerType.B, "Geweldig Idee");
        answers.put(AnswerType.C, "Wazig Idee");
        answers.put(AnswerType.D, "Raar Idee");
        return question;
    }
}
