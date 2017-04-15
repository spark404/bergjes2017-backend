package net.strocamp.bergjes.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Created by hugo on 15/04/2017.
 */
@DynamoDBTable(tableName = "locations")
public class Location {
    private String locationCode;
    private String roundOneQuestion;
    private String roundTwoQuestion;
    private String roundThreeQuestion;

    @DynamoDBHashKey
    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    @DynamoDBAttribute
    public String getRoundOneQuestion() {
        return roundOneQuestion;
    }

    public void setRoundOneQuestion(String roundOneQuestion) {
        this.roundOneQuestion = roundOneQuestion;
    }

    @DynamoDBAttribute
    public String getRoundTwoQuestion() {
        return roundTwoQuestion;
    }

    public void setRoundTwoQuestion(String roundTwoQuestion) {
        this.roundTwoQuestion = roundTwoQuestion;
    }

    @DynamoDBAttribute
    public String getRoundThreeQuestion() {
        return roundThreeQuestion;
    }

    public void setRoundThreeQuestion(String roundThreeQuestion) {
        this.roundThreeQuestion = roundThreeQuestion;
    }
}
