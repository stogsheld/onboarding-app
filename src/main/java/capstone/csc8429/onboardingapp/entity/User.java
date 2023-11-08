package capstone.csc8429.onboardingapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "join_date")
    private Date joinDate;

    @Column(name = "career_level")
    private String careerLevel;

    @Column(name = "team_id")
    private String teamId;

    @Column(name = "email")
    private String email;


    // Constructors
    public User() {
    }

    public User(int userId, String firstName, String lastName, Date joinDate,
                String careerLevel, String teamId, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.joinDate = joinDate;
        this.careerLevel = careerLevel;
        this.teamId = teamId;
        this.email = email;
    }


    // Getters/Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getCareerLevel() {
        return careerLevel;
    }

    public void setCareerLevel(String careerLevel) {
        this.careerLevel = careerLevel;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    //toString()
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", joinDate=" + joinDate +
                ", careerLevel='" + careerLevel + '\'' +
                ", teamId='" + teamId + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
