package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.dao.QuestionRepo;
import capstone.csc8429.onboardingapp.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepo questionRepo;

    @Autowired
    public QuestionServiceImpl(QuestionRepo theQuestionRepo) {
        questionRepo = theQuestionRepo;
    }

    @Override
    public List<Question> findAll() {
        return questionRepo.findAll();
    }

    @Override
    public Question findById(int theId) {
        Optional<Question> result = questionRepo.findById(theId);

        Question theQuestion  = null;

        if (result.isPresent()) {
            theQuestion = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find question for id - " + theId);
        }

        return theQuestion;
    }

    @Override
    public List<Question> findAllByCourseId(int courseId) {
        return questionRepo.findAllByCourseId(courseId);
    }

    @Override
    public void save(Question theQuestion) {
        questionRepo.save(theQuestion);
    }

    @Override
    public void deleteById(int theId) {
        questionRepo.deleteById(theId);
    }
}
