package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.strocamp.bergjes.domain.location.Location;
import net.strocamp.bergjes.domain.location.LocationResponse;

/**
 * Created by hugo on 10/04/2017.
 */
public class ScannedLocationRequestHandler implements RequestHandler<Location, LocationResponse> {

    public LocationResponse handleRequest(Location scanData, Context context) {
        return new LocationResponse();
    }
}
