package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.strocamp.bergjes.db.Database;
import net.strocamp.bergjes.domain.DeviceInfo;
import net.strocamp.bergjes.domain.Status;
import net.strocamp.bergjes.domain.StatusType;

import java.time.LocalDateTime;

/**
 * Created by hugo on 08/04/2017.
 */
public class StatusRequestHandler implements RequestHandler<DeviceInfo, Status> {

    private Database database;

    public StatusRequestHandler() {
        this.database = new Database();
    }

    public Status handleRequest(DeviceInfo deviceInfo, Context context) {
        Status status = new Status();
        status.setStatus(StatusType.OK);
        status.setTimestamp(LocalDateTime.now());

        database.storeTest(deviceInfo);
        return status;
    }
}
