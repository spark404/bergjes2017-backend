package net.strocamp.bergjes.domain.trade;

import net.strocamp.bergjes.domain.resource.Resource;

/**
 * Created by hugo on 25/04/2017.
 */
public class TradeResponse {
    private Resource gainedResource;

    public Resource getGainedResource() {
        return gainedResource;
    }

    public void setGainedResource(Resource gainedResource) {
        this.gainedResource = gainedResource;
    }
}
