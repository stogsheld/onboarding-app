package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.entity.Completion;

import java.util.List;

public interface CompletionService {

    List<Completion> findAll();

    Completion findById(int theId);

    List<Completion> findByUserId(int userId);

    void save(Completion theCompletion);

    void deleteById(int theId);
}
