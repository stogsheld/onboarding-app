package capstone.csc8429.onboardingapp.dao;

import capstone.csc8429.onboardingapp.entity.Completion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompletionRepo extends JpaRepository<Completion, Integer> {

    public List<Completion> findByUserId(int userId);
}
