package capstone.csc8429.onboardingapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "onboarding_info")
public class Onboarding {

    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "welcome_completion")
    private int welcomeCompletion;

    @Column(name = "data_ethics_completion")
    private int DataEthicsCompletion;

    @Column(name = "setup_completion")
    private int setupCompletion;

    @Column(name = "meet_team_completion")
    private int meetTeamCompletion;


    // Constructors
    public Onboarding() {
    }

    public Onboarding(int userId, int welcomeCompletion, int dataEthicsCompletion,
                      int setupCompletion, int meetTeamCompletion) {
        this.userId = userId;
        this.welcomeCompletion = welcomeCompletion;
        DataEthicsCompletion = dataEthicsCompletion;
        this.setupCompletion = setupCompletion;
        this.meetTeamCompletion = meetTeamCompletion;
    }


    // Getters/Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWelcomeCompletion() {
        return welcomeCompletion;
    }

    public void setWelcomeCompletion(int welcomeCompletion) {
        this.welcomeCompletion = welcomeCompletion;
    }

    public int getDataEthicsCompletion() {
        return DataEthicsCompletion;
    }

    public void setDataEthicsCompletion(int dataEthicsCompletion) {
        DataEthicsCompletion = dataEthicsCompletion;
    }

    public int getSetupCompletion() {
        return setupCompletion;
    }

    public void setSetupCompletion(int setupCompletion) {
        this.setupCompletion = setupCompletion;
    }

    public int getMeetTeamCompletion() {
        return meetTeamCompletion;
    }

    public void setMeetTeamCompletion(int meetTeamCompletion) {
        this.meetTeamCompletion = meetTeamCompletion;
    }


    // toString()

    @Override
    public String toString() {
        return "Onboarding{" +
                "userId=" + userId +
                ", welcomeCompletion=" + welcomeCompletion +
                ", DataEthicsCompletion=" + DataEthicsCompletion +
                ", setupCompletion=" + setupCompletion +
                ", meetTeamCompletion=" + meetTeamCompletion +
                '}';
    }
}
