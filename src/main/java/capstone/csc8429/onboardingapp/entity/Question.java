package capstone.csc8429.onboardingapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "course_question")
public class Question {

    @Id
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "question_id")
    private int questionId;

    @Column(name = "question_content")
    private String questionContent;


    // Constructors
    public Question() {
    }

    public Question(int courseId, int questionId, String questionContent) {
        this.courseId = courseId;
        this.questionId = questionId;
        this.questionContent = questionContent;
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
                "courseId=" + courseId +
                ", questionId=" + questionId +
                ", questionContent='" + questionContent + '\'' +
                '}';
    }
}
