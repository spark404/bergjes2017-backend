package net.strocamp.bergjes.db;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import net.strocamp.bergjes.domain.DeviceInfo;
import org.joda.time.LocalDateTime;

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

   public void storeTest(DeviceInfo deviceInfo) {
       Map<String, AttributeValue> dbItem = new HashMap<String, AttributeValue>();
       dbItem.put("deviceId", new AttributeValue().withS(deviceInfo.getDeviceIdentifier()));
       dbItem.put("timestamp", new AttributeValue().withS(LocalDateTime.now().toString()));
       dynamoDB.putItem("devices", dbItem);
   }
}
