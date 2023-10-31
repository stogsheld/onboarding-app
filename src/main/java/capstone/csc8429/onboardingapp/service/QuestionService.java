package capstone.csc8429.onboardingapp.service;


import capstone.csc8429.onboardingapp.entity.Question;

import java.util.List;

public interface QuestionService {

    List<Question> findAll();

    Question findById(int theId);

    List <Question> findQuestionsByCourseId(int courseId);

    void save(Question theQuestion);

    void deleteById(int theId);
}
