package net.strocamp.bergjes.domain.answer;

/**
 * Created by hugo on 11/04/2017.
 */
public class AnswerResponse {
    private AnswerStatus answerStatus;
    private String resource;
    private Integer gained;

    public AnswerStatus getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(AnswerStatus answerStatus) {
        this.answerStatus = answerStatus;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Integer getGained() {
        return gained;
    }

    public void setGained(Integer gained) {
        this.gained = gained;
    }
}
