package net.strocamp.bergjes.domain.resource;

/**
 * Created by hugo on 11/04/2017.
 */
public class Resource {
    private ResourceType resourceType;
    private Integer amount;

    public Resource() {
    }

    public Resource(ResourceType resourceType, Integer amount) {
        this.resourceType = resourceType;
        this.amount = amount;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
