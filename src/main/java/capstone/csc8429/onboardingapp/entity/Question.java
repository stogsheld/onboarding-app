package capstone.csc8429.onboardingapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course_question")
public class Question {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int questionId;

    @Column(name = "question_number")
    private int questionNumber;

    @Column(name = "course_id")
    private int courseId;

    @Column(name = "question_content")
    private String questionContent;

    @Column(name = "answer_one")
    private String answerOne;

    @Column(name = "answer_two")
    private String answerTwo;

    @Column(name = "answer_three")
    private String answerThree;

    @Column(name = "answer_four")
    private String answerFour;

    @Column(name = "correct_answer")
    private int correctAnswer;


    // Constructors
    public Question() {
    }

    public Question(int questionId, int questionNumber, int courseId,
                    String questionContent, String answerOne, String answerTwo,
                    String answerThree, String answerFour, int correctAnswer) {
        this.questionId = questionId;
        this.questionNumber = questionNumber;
        this.courseId = courseId;
        this.questionContent = questionContent;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.answerFour = answerFour;
        this.correctAnswer = correctAnswer;
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

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = answerThree;
    }

    public String getAnswerFour() {
        return answerFour;
    }

    public void setAnswerFour(String answerFour) {
        this.answerFour = answerFour;
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
        return "Question{" +
                "questionId=" + questionId +
                ", questionNumber=" + questionNumber +
                ", courseId=" + courseId +
                ", questionContent='" + questionContent + '\'' +
                ", answerOne='" + answerOne + '\'' +
                ", answerTwo='" + answerTwo + '\'' +
                ", answerThree='" + answerThree + '\'' +
                ", answerFour='" + answerFour + '\'' +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
