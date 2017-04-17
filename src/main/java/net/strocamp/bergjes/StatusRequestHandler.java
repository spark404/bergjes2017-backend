package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.strocamp.bergjes.db.Database;
import net.strocamp.bergjes.db.TeamStatus;
import net.strocamp.bergjes.domain.DeviceInfo;
import net.strocamp.bergjes.domain.Status;
import net.strocamp.bergjes.domain.answer.AnswerStatus;
import net.strocamp.bergjes.domain.internal.DeviceDetails;
import net.strocamp.bergjes.domain.question.Question;
import net.strocamp.bergjes.domain.resource.Resource;
import net.strocamp.bergjes.domain.resource.ResourceType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hugo on 08/04/2017.
 */
public class StatusRequestHandler extends AbstractRequestHandler implements RequestHandler<DeviceInfo, Status> {

    public Status handleRequest(DeviceInfo deviceInfo, Context context) {
        // Take care of the device update, this will also create the team if it doesn't exist
        DeviceDetails deviceDetails = new DeviceDetails();
        deviceDetails.setDeviceIdentifier(deviceInfo.getDeviceIdentifier());
        deviceDetails.setInstallationId(context.getClientContext().getClient().getInstallationId());
        deviceDetails.setLastSeen(LocalDateTime.now());
        database.updateStatus(deviceDetails);

        // The team should exist, no initialize and continue
        init(context);

        logger.log(String.format("Device details : %s, %s, %s",
                deviceDetails.getDeviceIdentifier(), deviceDetails.getInstallationId(), deviceDetails.getLastSeen()));

        String roundCode = settings.get("currentRound");
        Status status = new Status();

        status.setActiveRound(roundCode);

        status.setRoundExpiry(LocalDateTime.now().plus(60, ChronoUnit.MINUTES));

        ArrayList<Question> activeQuestions = new ArrayList<>();
        teamStatus.getQuestions()
                    .forEach((questionKey, questionStatus) -> {
                        logger.log(String.format("Adding question %s for team %s", questionKey, teamStatus.getTeamId()));
                        Question question = Question.fromDbQuestion(database.getQuestionbyKey(questionKey));
                        question.setAnswerStatus(AnswerStatus.valueOf(questionStatus.get("status")));
                        activeQuestions.add(question);
                    });
        status.setActiveQuestions(activeQuestions);

        ArrayList<Resource> resourceList = new ArrayList<>();
        teamStatus.getResources().forEach((resource, amount) -> {
            ResourceType type = ResourceType.valueOf(resource);
            logger.log(String.format("Adding %d of resource %s for team %s", amount, resource, teamStatus.getTeamId()));
            resourceList.add(new Resource(type, amount));
        });
        status.setResourceList(resourceList);

        return status;
    }
}
