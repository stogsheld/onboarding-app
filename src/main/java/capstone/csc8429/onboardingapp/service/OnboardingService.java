package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.entity.Onboarding;

import java.util.List;

public interface OnboardingService {

    List<Onboarding> findAll();

    Onboarding findById(int theId);

    void save(Onboarding theOnboarding);
}
