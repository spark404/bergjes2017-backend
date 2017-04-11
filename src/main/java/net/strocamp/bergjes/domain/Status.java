package net.strocamp.bergjes.domain;

import net.strocamp.bergjes.domain.question.Question;
import net.strocamp.bergjes.domain.resource.Resource;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hugo on 08/04/2017.
 */
public class Status {
    private String activeRound;
    private LocalDateTime roundExpiry;
    private List<Resource> resourceList;
    private List<Question> activeQuestions;

    public String getActiveRound() {
        return activeRound;
    }

    public void setActiveRound(String activeRound) {
        this.activeRound = activeRound;
    }

    public LocalDateTime getRoundExpiry() {
        return roundExpiry;
    }

    public void setRoundExpiry(LocalDateTime roundExpiry) {
        this.roundExpiry = roundExpiry;
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public List<Question> getActiveQuestions() {
        return activeQuestions;
    }

    public void setActiveQuestions(List<Question> activeQuestions) {
        this.activeQuestions = activeQuestions;
    }
}
