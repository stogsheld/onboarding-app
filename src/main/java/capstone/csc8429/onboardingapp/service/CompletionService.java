package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.entity.Completion;

import java.util.List;


// Completion Service so that the app can implement JPARepository DB methods in CompletionRepo
public interface CompletionService {

    List<Completion> findAll();

    Completion findById(int theId);

    List<Completion> findByUserId(int userId);

    Completion findByUserIdAndCourseId(int userId, int courseId);

    void save(Completion theCompletion);

    void deleteById(int theId);
}
