package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.entity.Answer;

import java.util.List;

public interface AnswerService {

    List<Answer> findAll();

    Answer findById(int theId);

    List<Answer> findByCourseIdAndQuestionId(int courseId, int questionId);

    void save(Answer theAnswer);

    void deleteById(int theId);
}
