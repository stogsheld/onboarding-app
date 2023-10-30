package capstone.csc8429.onboardingapp.dao;

import capstone.csc8429.onboardingapp.entity.Onboarding;
import org.springframework.data.jpa.repository.JpaRepository;

// Course repository - so app is able to access onboarding_info table
public interface OnboardingRepo extends JpaRepository<Onboarding, Integer> {
}
