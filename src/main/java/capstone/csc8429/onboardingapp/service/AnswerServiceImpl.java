package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.dao.AnswerRepo;
import capstone.csc8429.onboardingapp.entity.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {

    private AnswerRepo answerRepo;

    @Autowired
    public AnswerServiceImpl(AnswerRepo theAnswerRepo) {
        answerRepo = theAnswerRepo;
    }


    @Override
    public List<Answer> findAll() {
        return answerRepo.findAll();
    }

    @Override
    public Answer findById(int theId) {
        Optional<Answer> result = answerRepo.findById(theId);

        Answer theAnswer  = null;

        if (result.isPresent()) {
            theAnswer = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find user for id - " + theId);
        }

        return theAnswer;
    }

    @Override
    public List<Answer> findByCourseIdAndQuestionId(int courseId, int questionId) {
        return answerRepo.findByCourseIdAndQuestionId(courseId, questionId);
    }

    @Override
    public void save(Answer theAnswer) {
        answerRepo.save(theAnswer);
    }

    @Override
    public void deleteById(int theId) {
        answerRepo.deleteById(theId);
    }
}
