package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.strocamp.bergjes.domain.DeviceInfo;
import net.strocamp.bergjes.domain.Status;
import net.strocamp.bergjes.domain.answer.AnswerStatus;
import net.strocamp.bergjes.domain.internal.DeviceDetails;
import net.strocamp.bergjes.domain.question.Question;
import net.strocamp.bergjes.domain.resource.Resource;
import net.strocamp.bergjes.domain.resource.ResourceType;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

/**
 * Created by hugo on 08/04/2017.
 */
public class StatusRequestHandler extends AbstractRequestHandler implements RequestHandler<DeviceInfo, Status> {
    private final static Logger LOG = Logger.getLogger(StatusRequestHandler.class);

    public Status handleRequest(DeviceInfo deviceInfo, Context context) {
        // Take care of the device update, this will also create the team if it doesn't exist
        DeviceDetails deviceDetails = new DeviceDetails();
        deviceDetails.setDeviceIdentifier(deviceInfo.getDeviceIdentifier());
        deviceDetails.setInstallationId(context.getClientContext().getClient().getInstallationId());
        deviceDetails.setLastSeen(LocalDateTime.now());
        database.updateStatus(deviceDetails);

        // The team should exist, no initialize and continue
        init(context);

        LOG.debug(String.format("[%s] Entering %s", teamStatus.getTeamName(), this.getClass().getSimpleName()));

        LOG.info(String.format("Device details : %s, %s, %s",
                deviceDetails.getDeviceIdentifier(), deviceDetails.getInstallationId(), deviceDetails.getLastSeen()));

        String roundCode = settings.get("currentRound");
        Status status = new Status();

        status.setActiveRound(roundCode);

        LocalDateTime roundEnd = LocalDateTime.parse(settings.get("currentEndTime"));
        status.setRoundExpiry(roundEnd
                .toInstant(ZoneOffset.UTC)
                .getEpochSecond());

        String teamName = teamStatus.getTeamName();

        status.setTeamActive(teamName != null && !teamName.isEmpty() && !teamName.startsWith("xx"));
        status.setTeamName(teamName);
        status.setTeamId(teamStatus.getTeamId());

        ArrayList<Question> activeQuestions = new ArrayList<>();
        teamStatus.getQuestions()
                    .forEach((questionKey, questionStatus) -> {
                        LOG.debug(String.format("Adding question %s for team %s", questionKey, teamStatus.getTeamId()));
                        Question question = Question.fromDbQuestion(database.getQuestionbyKey(questionKey));
                        question.setAnswerStatus(AnswerStatus.valueOf(questionStatus.get("status")));
                        activeQuestions.add(question);
                    });
        status.setActiveQuestions(activeQuestions);

        ArrayList<Resource> resourceList = new ArrayList<>();
        teamStatus.getResources().forEach((resource, amount) -> {
            ResourceType type = ResourceType.valueOf(resource);
            LOG.debug(String.format("Adding %d of resource %s for team %s", amount, resource, teamStatus.getTeamId()));
            resourceList.add(new Resource(type, amount));
        });
        status.setResourceList(resourceList);

        return status;
    }
}
