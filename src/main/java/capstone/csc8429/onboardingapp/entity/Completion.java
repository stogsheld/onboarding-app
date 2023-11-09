package capstone.csc8429.onboardingapp.entity;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Date;

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
    private Date attemptDate;

    @Column(name = "completion_date")
    private Date completionDate;

    @Column(name = "refresher_date")
    private Date refresherDate;


    // Constructors
    public Completion() {
    }

    public Completion(int userId, int courseId, int attemptScore, Date attemptDate, Date completionDate, Date refresherDate) {
        this.userId = userId;
        this.courseId = courseId;
        this.attemptScore = attemptScore;
        this.attemptDate = attemptDate;
        this.completionDate = completionDate;
        this.refresherDate = refresherDate;
    }

    public Completion(int userId, int courseId, int attemptScore, Date attemptDate) {
        this.userId = userId;
        this.courseId = courseId;
        this.attemptScore = attemptScore;
        this.attemptDate = attemptDate;
    }

    public int getAttemptNo() {
        return attemptNo;
    }

    public void setAttemptNo(int attemptNo) {
        this.attemptNo = attemptNo;
    }

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

    public int getAttemptScore() {
        return attemptScore;
    }

    public void setAttemptScore(int attemptScore) {
        this.attemptScore = attemptScore;
    }

    public Date getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(Date attemptDate) {
        this.attemptDate = attemptDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Date getRefresherDate() {
        return refresherDate;
    }

    public void setRefresherDate(Date refresherDate) {
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
