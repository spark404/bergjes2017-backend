package net.strocamp.bergjes.domain.location;

import net.strocamp.bergjes.domain.question.Question;
import net.strocamp.bergjes.domain.resource.Resource;

/**
 * Created by hugo on 11/04/2017.
 */
public class LocationResponse {
    private Boolean locationAvailable;
    private Question question;
    private Resource resource;

    public Boolean getLocationAvailable() {
        return locationAvailable;
    }

    public void setLocationAvailable(Boolean locationAvailable) {
        this.locationAvailable = locationAvailable;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
