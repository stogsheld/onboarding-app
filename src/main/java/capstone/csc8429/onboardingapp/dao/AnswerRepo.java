package capstone.csc8429.onboardingapp.dao;

import capstone.csc8429.onboardingapp.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepo extends JpaRepository<Answer, Integer> {

    public List<Answer> findByCourseIdAndQuestionId(int courseId, int questionId);

}
