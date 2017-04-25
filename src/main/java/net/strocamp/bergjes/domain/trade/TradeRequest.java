package net.strocamp.bergjes.domain.trade;

import net.strocamp.bergjes.domain.resource.Resource;

/**
 * Created by hugo on 25/04/2017.
 */
public class TradeRequest {
    // Team id of the party offering goods
    private String supplierId;
    private Resource suppliedResource;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Resource getSuppliedResource() {
        return suppliedResource;
    }

    public void setSuppliedResource(Resource suppliedResource) {
        this.suppliedResource = suppliedResource;
    }
}
