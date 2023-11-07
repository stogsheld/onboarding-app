package capstone.csc8429.onboardingapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "course_answers")
public class Attempt {

    @Id
    @Column(name = "attempt_id")
    private int attemptId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "attempt_no")
    private int attemptNo;

    @Column(name = "answer_one")
    private int answerOne;

    @Column(name = "answer_two")
    private int answerTwo;

    @Column(name = "answer_three")
    private int answerThree;

    @Column(name = "answer_four")
    private int answerFour;

    @Column(name = "answer_five")
    private int answerFive;

    public Attempt() {
    }

    public Attempt(int userId, int attemptNo) {
        this.userId = userId;
        this.attemptNo = attemptNo;
    }


    public int getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(int attemptId) {
        this.attemptId = attemptId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAttemptNo() {
        return attemptNo;
    }

    public void setAttemptNo(int attemptNo) {
        this.attemptNo = attemptNo;
    }

    public int getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(int answerOne) {
        this.answerOne = answerOne;
    }

    public int getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(int answerTwo) {
        this.answerTwo = answerTwo;
    }

    public int getAnswerThree() {
        return answerThree;
    }

    public void setAnswerThree(int answerThree) {
        this.answerThree = answerThree;
    }

    public int getAnswerFour() {
        return answerFour;
    }

    public void setAnswerFour(int answerFour) {
        this.answerFour = answerFour;
    }

    public int getAnswerFive() {
        return answerFive;
    }

    public void setAnswerFive(int answerFive) {
        this.answerFive = answerFive;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "attemptId=" + attemptId +
                ", userId=" + userId +
                ", attemptNo=" + attemptNo +
                ", answerOne=" + answerOne +
                ", answerTwo=" + answerTwo +
                ", answerThree=" + answerThree +
                ", answerFour=" + answerFour +
                ", answerFive=" + answerFive +
                '}';
    }
}
