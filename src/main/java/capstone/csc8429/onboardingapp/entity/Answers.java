package capstone.csc8429.onboardingapp.entity;

import java.util.List;


public class Answers {

    private List<Integer> responses;

    public Answers() {
    }

    public Answers(List<Integer> responses) {
        this.responses = responses;
    }

    public List<Integer> getResponses() {
        return responses;
    }

    public void setResponses(List<Integer> responses) {
        this.responses = responses;
    }
}
