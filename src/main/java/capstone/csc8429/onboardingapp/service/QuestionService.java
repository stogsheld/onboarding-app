package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.entity.Question;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


// Question Service so that the app can implement JPARepository DB methods in QuestionRepo
public interface QuestionService {

    List<Question> findAll();

    Question findById(int theId);

    List <Question> findAllByCourseId(int courseId);

    void save(Question theQuestion);

    void deleteById(int theId);

    @Transactional
    void deleteByCourseId(int id);
}
