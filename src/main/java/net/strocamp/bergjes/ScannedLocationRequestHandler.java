package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.strocamp.bergjes.domain.location.Location;
import net.strocamp.bergjes.domain.location.LocationResponse;
import net.strocamp.bergjes.domain.question.Question;
import net.strocamp.bergjes.domain.resource.Resource;
import net.strocamp.bergjes.domain.resource.ResourceType;
import net.strocamp.bergjes.exceptions.NoSuchLocationException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by hugo on 10/04/2017.
 */
public class ScannedLocationRequestHandler extends AbstractRequestHandler implements RequestHandler<Location, LocationResponse> {
    private final static Logger LOG = Logger.getLogger(ScannedLocationRequestHandler.class);
    private final static Random RANDOM = new Random();

    public LocationResponse handleRequest(Location scanData, Context context) {
        init(context);

        LOG.debug(String.format("[%s] Entering %s", teamStatus.getTeamName(), this.getClass().getSimpleName()));

        String roundCode = settings.get("currentRound");

        LocationResponse locationResponse = new LocationResponse();
        if (teamStatus.getVisitedLocationsPerRound().containsKey(roundCode) &&
                teamStatus.getVisitedLocationsPerRound().get(roundCode).contains(scanData.getLocationCode())) {
            // Been here before

            locationResponse.setLocationAvailable(false);
            LOG.debug(String.format("[%s] Already visited location %s during round %s",
                    teamStatus.getTeamName(), scanData.getLocationCode(), roundCode));
        } else {
            locationResponse.setLocationAvailable(true);

            net.strocamp.bergjes.db.Location dbLocation = database.getLocationByKey(scanData.getLocationCode());
            if (dbLocation == null) {
                String message = String.format("[%s] No location %s in database",
                        teamStatus.getTeamName(), scanData.getLocationCode());
                LOG.error(message);
                throw new NoSuchLocationException(message);
            }
            Map<String, String> currentRoundData = dbLocation.getRoundData().get(roundCode);
            String questionCode = currentRoundData.get("questionKey");

            locationResponse.setQuestion(Question.fromDbQuestion(database.getQuestionbyKey(questionCode)));
            ResourceType resourceType = ResourceType.valueOf(currentRoundData.get("resourceType"));
            if (ResourceType.RANDOM.equals(resourceType)) {
                resourceType = ResourceType.randomResource();
            }
            locationResponse.setResource(new Resource(resourceType, 0));

            if (!teamStatus.getVisitedLocationsPerRound().containsKey(roundCode)) {
                teamStatus.getVisitedLocationsPerRound().put(roundCode, new ArrayList<>());
            }

            teamStatus.getVisitedLocationsPerRound().get(roundCode).add(scanData.getLocationCode());
            HashMap<String, String> questionStatus = new HashMap<String, String>();
            questionStatus.put("status", "OPEN");
            questionStatus.put("resource", resourceType.name());
            int resourceMin = Integer.parseInt(currentRoundData.get("resourceMin"));
            int resourceMax = Integer.parseInt(currentRoundData.get("resourceMax"));
            questionStatus.put("amount", Integer.toString(getRandomAmount(resourceMin, resourceMax)));

            teamStatus.getQuestions().put(questionCode, questionStatus);
            database.updateTeamStatus(teamStatus);

            LOG.debug(String.format("[%s] Unlocked question %s at location %s during round %s for %d of type %s",
                    teamStatus.getTeamName(), questionCode, scanData.getLocationCode(), roundCode,
                    locationResponse.getResource().getAmount(),
                    locationResponse.getResource().getResourceType().name()));
        }

        return locationResponse;
    }

    private int getRandomAmount(int lower, int upper) {
        return RANDOM.nextInt((upper + 1) - lower) + lower;
    }
}
