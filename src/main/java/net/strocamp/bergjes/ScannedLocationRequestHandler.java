package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.strocamp.bergjes.domain.answer.AnswerType;
import net.strocamp.bergjes.domain.location.Location;
import net.strocamp.bergjes.domain.location.LocationResponse;
import net.strocamp.bergjes.domain.question.Question;
import net.strocamp.bergjes.domain.resource.Resource;
import net.strocamp.bergjes.domain.resource.ResourceType;

import java.util.HashMap;

/**
 * Created by hugo on 10/04/2017.
 */
public class ScannedLocationRequestHandler implements RequestHandler<Location, LocationResponse> {

    public LocationResponse handleRequest(Location scanData, Context context) {
        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setLocationAvailable(false);
        locationResponse.setQuestion(getQuestion());
        locationResponse.setResource(getResource());

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
