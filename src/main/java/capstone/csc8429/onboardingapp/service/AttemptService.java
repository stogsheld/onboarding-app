package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.entity.Attempt;

import java.util.List;

public interface AttemptService {

    List<Attempt> findAll();

    Attempt findByUserIdAndAttemptNo(int userId, int attemptNo);

    void save (Attempt theAttempt);

    void delete(Attempt theAttempt);
}
