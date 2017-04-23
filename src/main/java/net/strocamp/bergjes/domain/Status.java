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
    private Long roundExpiry;
    private List<Resource> resourceList;
    private List<Question> activeQuestions;
    private Boolean teamActive;
    private String teamName;
    private String teamId;

    public String getActiveRound() {
        return activeRound;
    }

    public void setActiveRound(String activeRound) {
        this.activeRound = activeRound;
    }

    public Long getRoundExpiry() {
        return roundExpiry;
    }

    public void setRoundExpiry(Long roundExpiry) {
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

    public Boolean getTeamActive() {
        return teamActive;
    }

    public void setTeamActive(Boolean teamActive) {
        this.teamActive = teamActive;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
