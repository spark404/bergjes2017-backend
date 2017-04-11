package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.strocamp.bergjes.db.Database;
import net.strocamp.bergjes.domain.DeviceInfo;
import net.strocamp.bergjes.domain.Status;
import net.strocamp.bergjes.domain.internal.DeviceDetails;
import net.strocamp.bergjes.domain.question.Question;
import net.strocamp.bergjes.domain.resource.Resource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

/**
 * Created by hugo on 08/04/2017.
 */
public class StatusRequestHandler implements RequestHandler<DeviceInfo, Status> {

    private Database database;

    public StatusRequestHandler() {
        this.database = new Database();
    }

    public Status handleRequest(DeviceInfo deviceInfo, Context context) {
        LambdaLogger logger = context.getLogger();

        DeviceDetails deviceDetails = new DeviceDetails();
        deviceDetails.setDeviceIdentifier(deviceInfo.getDeviceIdentifier());
        deviceDetails.setInstallationId(context.getClientContext().getClient().getInstallationId());
        deviceDetails.setLastSeen(LocalDateTime.now());

        logger.log(String.format("Device details : %s, %s, %s",
                deviceDetails.getDeviceIdentifier(), deviceDetails.getInstallationId(), deviceDetails.getLastSeen()));

        database.updateStatus(deviceDetails);

        Status status = new Status();
        status.setActiveRound("ronde1");
        status.setRoundExpiry(LocalDateTime.now().plus(60, ChronoUnit.MINUTES));
        status.setActiveQuestions(Collections.<Question>emptyList());
        status.setResourceList(Collections.<Resource>emptyList());

        return status;
    }
}
