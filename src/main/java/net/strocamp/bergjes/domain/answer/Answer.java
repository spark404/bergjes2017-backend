package net.strocamp.bergjes.domain.answer;

/**
 * Created by hugo on 11/04/2017.
 */
public class Answer {
    private String questionKey;
    private AnswerType answer;

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }

    public AnswerType getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerType answer) {
        this.answer = answer;
    }
}
