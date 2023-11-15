package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.entity.Onboarding;

import java.util.List;


// Onboarding Service so that the app can implement JPARepository DB methods in OnboardingRepo
public interface OnboardingService {

    List<Onboarding> findAll();

    Onboarding findById(int theId);

    void save(Onboarding theOnboarding);

    void deleteById(int theId);
}
