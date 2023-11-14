package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.dao.CompletionRepo;
import capstone.csc8429.onboardingapp.entity.Completion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompletionServiceImpl implements CompletionService{

    private CompletionRepo completionRepo;

    @Autowired
    public CompletionServiceImpl(CompletionRepo theCompletionRepo) {
        completionRepo = theCompletionRepo;
    }

    @Override
    public List<Completion> findAll() {
        return completionRepo.findAll();
    }

    @Override
    public Completion findById(int theId) {
        Optional<Completion> result = completionRepo.findById(theId);

        Completion theCompletion = null;

        if (result.isPresent()) {
            theCompletion = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find user for id - " + theId);
        }

        return theCompletion;
    }

    @Override
    public List<Completion> findByUserId(int userId) {
        return completionRepo.findByUserId(userId);
    }

    @Override
    public Completion findByUserIdAndCourseId(int userId, int courseId) {
        return completionRepo.findByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public void save(Completion theCompletion) {
        completionRepo.save(theCompletion);
    }

    @Override
    public void deleteById(int theId) {
        completionRepo.deleteById(theId);
    }
}
