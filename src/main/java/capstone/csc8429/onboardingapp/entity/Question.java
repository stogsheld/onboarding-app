package capstone.csc8429.onboardingapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "course_question")
public class Question {

    @Id
    @Column(name = "question_id")
    private int questionId;

    @Column(name = "question_number")
    private int questionNumber;

    @Column(name = "course_id")
    private int courseId;

    @Column(name = "question_content")
    private String questionContent;


    // Constructors
    public Question() {
    }

    public Question(int questionId, int questionNumber, int courseId, String questionContent) {
        this.questionId = questionId;
        this.questionNumber = questionNumber;
        this.courseId = courseId;
        this.questionContent = questionContent;
    }

    // Getters/Setters
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }


    // toString()

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", questionNumber=" + questionNumber +
                ", courseId=" + courseId +
                ", questionContent='" + questionContent + '\'' +
                '}';
    }
}
