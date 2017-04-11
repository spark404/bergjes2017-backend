package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.strocamp.bergjes.domain.ScanData;
import net.strocamp.bergjes.domain.ScanResult;

/**
 * Created by hugo on 10/04/2017.
 */
public class NewLocationRequestHandler implements RequestHandler<ScanData, ScanResult> {

    public ScanResult handleRequest(ScanData scanData, Context context) {
        ScanResult scanResult = new ScanResult();
        scanResult.setEgg(2);
        return scanResult;
    }
}
