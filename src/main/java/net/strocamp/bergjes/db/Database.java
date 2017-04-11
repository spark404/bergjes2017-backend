package net.strocamp.bergjes.db;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import net.strocamp.bergjes.domain.internal.DeviceDetails;
import net.strocamp.bergjes.domain.question.Question;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by hugo on 10/04/2017.
 */
public class Database {
    private static AmazonDynamoDB dynamoDB;

    static {
        dynamoDB = AmazonDynamoDBClient.builder()
                .withRegion(Regions.EU_WEST_2)
                .build();
    }

   public void updateStatus(DeviceDetails deviceDetails) {

       Map<String, AttributeValue> dbItem = new HashMap<String, AttributeValue>();
       dbItem.put("deviceId", new AttributeValue().withS(deviceDetails.getDeviceIdentifier()));
       dbItem.put("installationId", new AttributeValue().withS(deviceDetails.getInstallationId()));
       dbItem.put("timestamp", new AttributeValue().withS(deviceDetails.getLastSeen().toString()));

       dynamoDB.putItem("devices", dbItem);
   }

}
