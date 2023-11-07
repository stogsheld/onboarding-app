package capstone.csc8429.onboardingapp.dao;

import capstone.csc8429.onboardingapp.entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptRepo extends JpaRepository<Attempt, Integer> {

    public Attempt findByUserIdAndAttemptNo (int userId, int attemptNo);
}
