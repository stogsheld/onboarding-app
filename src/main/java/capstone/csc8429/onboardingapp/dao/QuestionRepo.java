package capstone.csc8429.onboardingapp.dao;

import capstone.csc8429.onboardingapp.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Integer> {

    public List<Question> findAllByCourseId(int courseId);

    public void deleteByCourseId(int courseId);

}
