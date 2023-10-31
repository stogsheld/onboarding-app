package capstone.csc8429.onboardingapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "course_answer")
public class Answer {

    @Id
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "question_id")
    private int questionId;

    @Column(name = "answer_id")
    private int answerId;

    @Column(name = "answer_content")
    private String answerContent;

    @Column(name = "correct_answer")
    private int correctAnswer;


    // Constructors
    public Answer() {
    }

    public Answer(int courseId, int questionId, int answerId, String answerContent, int correctAnswer) {
        this.courseId = courseId;
        this.questionId = questionId;
        this.answerId = answerId;
        this.answerContent = answerContent;
        this.correctAnswer = correctAnswer;
    }


    // Getters/Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


    // toString()
    @Override
    public String toString() {
        return "Answer{" +
                "courseId=" + courseId +
                ", questionId=" + questionId +
                ", answerId=" + answerId +
                ", answerContent='" + answerContent + '\'' +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
