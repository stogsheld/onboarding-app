package capstone.csc8429.onboardingapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course_answer")
public class Answer {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private int answerId;

    @Column(name = "answer_content")
    private String answerContent;

    @Column(name = "correct_answer")
    private int correctAnswer;

    @Column(name = "course_id")
    private int courseId;

    @Column(name = "question_number")
    private int questionNumber;



    // Constructors
    public Answer() {
    }

    public Answer(int courseId, int questionNumber, int answerId, String answerContent, int correctAnswer) {
        this.courseId = courseId;
        this.questionNumber = questionNumber;
        this.answerId = answerId;
        this.answerContent = answerContent;
        this.correctAnswer = correctAnswer;
    }


    // Getters/Setters
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }


    // toString()

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", answerContent='" + answerContent + '\'' +
                ", correctAnswer=" + correctAnswer +
                ", courseId=" + courseId +
                ", questionNumber=" + questionNumber +
                '}';
    }
}
