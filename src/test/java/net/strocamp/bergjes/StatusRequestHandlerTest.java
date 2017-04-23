package net.strocamp.bergjes;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

/**
 * Created by hugo on 23/04/2017.
 */
public class StatusRequestHandlerTest {

    @Test
    public void datesTesting() {
        String databaseValue = "2017-04-23T16:03:25.313";
        LocalDateTime roundEnd = LocalDateTime.parse(databaseValue);
        System.out.println(roundEnd.toString());
    }

}