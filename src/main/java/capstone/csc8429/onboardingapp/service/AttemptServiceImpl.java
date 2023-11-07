package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.dao.AttemptRepo;
import capstone.csc8429.onboardingapp.entity.Attempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttemptServiceImpl implements AttemptService{

    private AttemptRepo attemptRepo;

    @Autowired
    public AttemptServiceImpl(AttemptRepo theAttemptRepo) {
        attemptRepo = theAttemptRepo;
    }

    @Override
    public List<Attempt> findAll() {
        return attemptRepo.findAll();
    }

    @Override
    public Attempt findByUserIdAndAttemptNo(int userId, int attemptNo) {
        return attemptRepo.findByUserIdAndAttemptNo(userId, attemptNo);
    }

    @Override
    public void save(Attempt theAttempt) {
        attemptRepo.save(theAttempt);
    }

    @Override
    public void delete(Attempt theAttempt) {
        attemptRepo.delete(theAttempt);
    }
}
