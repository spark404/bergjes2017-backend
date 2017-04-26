package net.strocamp.bergjes.geolocate;

import org.junit.Test;

/**
 * Created by hugo on 26/04/2017.
 */
public class DistanceCalculatorTest {
    @Test
    public void distance() throws Exception {
        //<+52.03431323,+5.15105558>
        //<+52.03431487,+5.15104265>
        double lat1 = 52.03431363602994;
        double lon1 = 5.151045506336327;

        double lat2 = 52.03431323;
        double lon2 = 5.15105558;

        double distance = DistanceCalculator.distance(lat1, lon1, lat2, lon2);
        System.out.println(distance);
    }

}