package net.strocamp.bergjes.domain.question;

import net.strocamp.bergjes.domain.answer.AnswerType;

import java.util.Map;

/**
 * Created by hugo on 11/04/2017.
 */
public class Question {
    private String question;
    private Map<AnswerType, String> answers;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<AnswerType, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<AnswerType, String> answers) {
        this.answers = answers;
    }
}
