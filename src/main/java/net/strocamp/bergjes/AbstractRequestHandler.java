package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import net.strocamp.bergjes.db.Database;
import net.strocamp.bergjes.db.TeamStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hugo on 15/04/2017.
 */
public abstract class AbstractRequestHandler {
    protected Database database = new Database();
    protected TeamStatus teamStatus;
    protected String installationId;
    protected Map<String, String> settings = new HashMap<>();

    protected void init(Context context) {
        installationId = context.getClientContext().getClient().getInstallationId();
        teamStatus = database.getTeamStatusFromContext(installationId);
        database.loadSettings(settings);
    }
}
