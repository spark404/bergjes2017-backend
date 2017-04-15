package net.strocamp.bergjes.db;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import net.strocamp.bergjes.domain.answer.AnswerType;
import net.strocamp.bergjes.domain.internal.DeviceDetails;
import net.strocamp.bergjes.domain.resource.ResourceType;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by hugo on 10/04/2017.
 */
public class Database {
    private static DynamoDBMapper mapper;

    static {
        AmazonDynamoDB dynamoDB = AmazonDynamoDBClient.builder()
                .withRegion(Regions.EU_WEST_2)
                .build();

        mapper = new DynamoDBMapper(dynamoDB);
    }

    public String updateStatus(DeviceDetails deviceDetails) {
        RegisteredDevice device = mapper.load(RegisteredDevice.class, deviceDetails.getInstallationId());
        if (device != null) {
            device.setLastSeen(deviceDetails.getLastSeen().toString());
            mapper.save(device);
        } else {
            RegisteredDevice registeredDevice = new RegisteredDevice();
            registeredDevice.setDeviceId(deviceDetails.getInstallationId());
            registeredDevice.setLastSeen(deviceDetails.getLastSeen().toString());
            registeredDevice.setTeamIdentifier(UUID.randomUUID().toString());

            TeamStatus teamStatus =  new TeamStatus();
            teamStatus.setTeamId(registeredDevice.getTeamIdentifier());
            teamStatus.setQuestions(Collections.emptyMap());
            teamStatus.setVisitedLocationsPerRound(Collections.emptyMap());
            HashMap<String, Integer> resources = getInitialResources();
            teamStatus.setResources(resources);

            mapper.batchSave(registeredDevice, teamStatus);
            device = registeredDevice;
        }

        return device.getTeamIdentifier();
    }

    public TeamStatus getTeamStatusFromContext(String installationid) {
        RegisteredDevice device = mapper.load(RegisteredDevice.class, installationid);
        if (device == null) {
            throw new IllegalStateException("InstallationId " + installationid + " doesn't exist");
        }

        return mapper.load(TeamStatus.class, device.getTeamIdentifier());
    }

    private HashMap<String, Integer> getInitialResources() {
        HashMap<String, Integer> resources = new HashMap<>();
        resources.put(ResourceType.BRICK.name(), 0);
        resources.put(ResourceType.FENCE.name(), 0);
        resources.put(ResourceType.CHICKEN.name(), 0);
        resources.put(ResourceType.GRAIN.name(), 0);
        resources.put(ResourceType.EGG.name(), 0);
        resources.put(ResourceType.WOOD.name(), 0);
        return resources;
    }

    public net.strocamp.bergjes.domain.question.Question getQuestionbyKey(String questionKey) {

        Question question = mapper.load(Question.class, questionKey);

        net.strocamp.bergjes.domain.question.Question questionModel =
                new net.strocamp.bergjes.domain.question.Question();
        questionModel.setQuestionKey(question.getQuestionKey());
        questionModel.setQuestion(question.getQuestion());
        HashMap<AnswerType, String> answers = new HashMap<>();
        answers.put(AnswerType.A, question.getAnswerA());
        answers.put(AnswerType.B, question.getAnswerB());
        answers.put(AnswerType.C, question.getAnswerC());
        answers.put(AnswerType.D, question.getAnswerD());
        questionModel.setAnswers(answers);

        return questionModel;
    }

    public Location getLocationByKey(String locationKey) {
        return mapper.load(Location.class, locationKey);
    }

    public TeamStatus getTeamStatusById(String teamIdentifier) {
        return mapper.load(TeamStatus.class, teamIdentifier);
    }

    public void updateTeamStatus(TeamStatus status) {
        mapper.save(status);
    }

    public void loadSettings(Map<String, String> settings) {
        mapper.scan(Setting.class, new DynamoDBScanExpression())
                .stream().forEach(setting -> settings.put(setting.getSettingKey(), setting.getSettingValue()));
    }

}
