package capstone.csc8429.onboardingapp.dao;

import capstone.csc8429.onboardingapp.entity.Completion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


// Course repository - so app is able to access course_completion table
public interface CompletionRepo extends JpaRepository<Completion, Integer> {

    // Find completion by User ID
    public List<Completion> findByUserId(int userId);

    // Get completion by User ID and Course ID
    public Completion findByUserIdAndCourseId(int userId, int courseId);
}
