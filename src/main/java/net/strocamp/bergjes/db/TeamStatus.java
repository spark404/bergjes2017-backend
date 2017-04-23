package net.strocamp.bergjes.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Map;

/**
 * Created by hugo on 15/04/2017.
 */
@DynamoDBTable(tableName = "teamstatus")
public class TeamStatus {
    // UUID from devices
    private String teamId;

    // Customizable name
    private String teamName;

    // round - > [locations]
    private Map<String, List<String>> visitedLocationsPerRound;

    // questionKey -> status
    private Map<String, Map<String,String>> questions;

    // resourceType -> amount
    private Map<String, Integer> resources;

    @DynamoDBHashKey
    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    @DynamoDBAttribute
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @DynamoDBAttribute
    public Map<String, List<String>> getVisitedLocationsPerRound() {
        return visitedLocationsPerRound;
    }

    public void setVisitedLocationsPerRound(Map<String, List<String>> visitedLocationsPerRound) {
        this.visitedLocationsPerRound = visitedLocationsPerRound;
    }

    @DynamoDBAttribute
    public Map<String, Map<String,String>> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<String, Map<String,String>> questions) {
        this.questions = questions;
    }

    @DynamoDBAttribute
    public Map<String, Integer> getResources() {
        return resources;
    }

    public void setResources(Map<String, Integer> resources) {
        this.resources = resources;
    }
}
