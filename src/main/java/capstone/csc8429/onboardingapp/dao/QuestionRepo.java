package capstone.csc8429.onboardingapp.dao;

import capstone.csc8429.onboardingapp.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


// Course repository - so app is able to access course_question table
public interface QuestionRepo extends JpaRepository<Question, Integer> {

    // Find all questions by course ID
    public List<Question> findAllByCourseId(int courseId);

    // Delete question by course ID
    public void deleteByCourseId(int courseId);

}
