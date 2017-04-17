package net.strocamp.bergjes.domain.question;

import net.strocamp.bergjes.domain.answer.AnswerStatus;
import net.strocamp.bergjes.domain.answer.AnswerType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hugo on 11/04/2017.
 */
public class Question {
    private String questionKey;
    private String question;
    private Map<AnswerType, String> answers;
    private AnswerStatus answerStatus;

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }

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

    public AnswerStatus getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(AnswerStatus answerStatus) {
        this.answerStatus = answerStatus;
    }

    public static Question fromDbQuestion(net.strocamp.bergjes.db.Question question) {
            Question questionModel = new Question();
            questionModel.setQuestionKey(question.getQuestionKey());
            questionModel.setQuestion(question.getQuestion());
            HashMap<AnswerType, String> answers = new HashMap<>();
            answers.put(AnswerType.A, question.getAnswerA());
            answers.put(AnswerType.B, question.getAnswerB());
            answers.put(AnswerType.C, question.getAnswerC());
            answers.put(AnswerType.D, question.getAnswerD());
            questionModel.setAnswers(answers);
            questionModel.setAnswerStatus(AnswerStatus.OPEN);

            return questionModel;
    }
}
