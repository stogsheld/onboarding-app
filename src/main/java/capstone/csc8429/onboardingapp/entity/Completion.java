package capstone.csc8429.onboardingapp.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "course_completion")
public class Completion {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "attempt_no")
    private int attemptNo;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "course_id")
    private int courseId;
    
    @Column(name = "attempt_score")
    private int attemptScore;

    @Column(name = "attempt_date")
    private String attemptDate;

    @Column(name = "completion_date")
    private String completionDate;

    @Column(name = "refresher_date")
    private String refresherDate;


    // Constructors
    public Completion() {
    }

    public Completion(int userId, int courseId, int attemptScore,
                      String attemptDate, String completionDate, String refresherDate) {
        this.userId = userId;
        this.courseId = courseId;
        this.attemptScore = attemptScore;
        this.attemptDate = attemptDate;
        this.completionDate = completionDate;
        this.refresherDate = refresherDate;
    }


    // Getters/Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getAttemptNo() {
        return attemptNo;
    }

    public void setAttemptNo(int attemptNo) {
        this.attemptNo = attemptNo;
    }

    public int getAttemptScore() {
        return attemptScore;
    }

    public void setAttemptScore(int attemptScore) {
        this.attemptScore = attemptScore;
    }

    public String getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(String attemptDate) {
        this.attemptDate = attemptDate;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getRefresherDate() {
        return refresherDate;
    }

    public void setRefresherDate(String refresherDate) {
        this.refresherDate = refresherDate;
    }

    // toString()
    @Override
    public String toString() {
        return "Completion{" +
                "userId=" + userId +
                ", courseId=" + courseId +
                ", attemptNo=" + attemptNo +
                ", attemptScore=" + attemptScore +
                ", attemptDate=" + attemptDate +
                ", completionDate=" + completionDate +
                ", refresherDate=" + refresherDate +
                '}';
    }
}
