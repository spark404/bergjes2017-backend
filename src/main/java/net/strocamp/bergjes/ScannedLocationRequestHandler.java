package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.strocamp.bergjes.db.GeoLocation;
import net.strocamp.bergjes.domain.location.Location;
import net.strocamp.bergjes.domain.location.LocationResponse;
import net.strocamp.bergjes.domain.question.Question;
import net.strocamp.bergjes.domain.resource.Resource;
import net.strocamp.bergjes.domain.resource.ResourceType;
import net.strocamp.bergjes.exceptions.LocationMismatchException;
import net.strocamp.bergjes.exceptions.NoSuchLocationException;
import net.strocamp.bergjes.geolocate.DistanceCalculator;
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

            verifyGeoLocation(dbLocation, scanData, teamStatus.getTeamName());

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

    private void verifyGeoLocation(net.strocamp.bergjes.db.Location dbLocation, Location scanData, String teamName) {
        if (dbLocation.getGeoLocation() == null
                || dbLocation.getGeoLocation().getLatitude() == null
                || dbLocation.getGeoLocation().getLongitude() == null) {
            // No location data, update the location
            LOG.info(String.format("[%s] Location %s has no geolocation data", teamName, scanData.getLocationCode()));

            if (scanData.getLatitude() != null && scanData.getLongitude() != null) {
                GeoLocation geoLocation = new GeoLocation(scanData.getLatitude(), scanData.getLongitude());
                dbLocation.setGeoLocation(geoLocation);
                database.updateLocation(dbLocation);
                LOG.info(String.format("[%s] Set geolocation for location %s to <%f,%f>", teamName,
                        scanData.getLocationCode(), scanData.getLatitude(), scanData.getLongitude()));
            }

            return;
        }

        GeoLocation geoLocation = dbLocation.getGeoLocation();
        double distance = DistanceCalculator.distance(geoLocation.getLatitude(), geoLocation.getLongitude(),
                scanData.getLatitude(), scanData.getLongitude());

        Boolean enforceDistance = Boolean.parseBoolean(settings.get("breakOnDistance"));

        LOG.info(String.format("[%s] Scanned location is %f meters away from stored location", teamName, distance));
        if (distance > (100 + 2 * scanData.getAccuracy()) && enforceDistance) {
            throw new LocationMismatchException(String.format("Jullie zijn %.1f meter verwijderd van deze locatie?!?", distance));
        }
    }

    private int getRandomAmount(int lower, int upper) {
        return RANDOM.nextInt((upper + 1) - lower) + lower;
    }
}
