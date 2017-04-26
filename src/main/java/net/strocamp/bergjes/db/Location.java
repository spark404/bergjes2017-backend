package net.strocamp.bergjes.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBFlattened;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Map;

/**
 * Created by hugo on 15/04/2017.
 */
@DynamoDBTable(tableName = "locations")
public class Location {
    private String locationCode;
    private Map<String, Map<String,String>> roundData;
    private GeoLocation geoLocation;

    /* {
          "locationCode": "BELC04",
          "roundData": {
            "round1": {
              "questionKey": "Q001",
              "resourceMax": "2",
              "resourceMin": "1",
              "resourceType": "EGG"
            },
            "round2": {
              "questionKey": "Q001",
              "resourceMax": "2",
              "resourceMin": "1",
              "resourceType": "EGG"
            },
            "round3": {
              "questionKey": "Q001",
              "resourceMax": "2",
              "resourceMin": "1",
              "resourceType": "EGG"
            }
          }
        }
    */


    @DynamoDBHashKey
    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    @DynamoDBAttribute
    public Map<String, Map<String, String>> getRoundData() {
        return roundData;
    }

    public void setRoundData(Map<String, Map<String, String>> roundData) {
        this.roundData = roundData;
    }

    @DynamoDBFlattened(attributes = {
            @DynamoDBAttribute(mappedBy = "latitude", attributeName = "latitude"),
            @DynamoDBAttribute(mappedBy = "longitude", attributeName = "longitude")})
    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }
}
