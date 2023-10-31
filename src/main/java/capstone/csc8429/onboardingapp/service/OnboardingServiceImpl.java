package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.dao.OnboardingRepo;
import capstone.csc8429.onboardingapp.entity.Onboarding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OnboardingServiceImpl implements OnboardingService{

    private OnboardingRepo onboardingRepo;

    @Autowired
    public OnboardingServiceImpl(OnboardingRepo theOnboardingRepo) {
        onboardingRepo = theOnboardingRepo;
    }


    @Override
    public List<Onboarding> findAll() {
        return onboardingRepo.findAll();
    }

    @Override
    public Onboarding findById(int theId) {
        Optional<Onboarding> result = onboardingRepo.findById(theId);

        Onboarding theOnboarding  = null;

        if (result.isPresent()) {
            theOnboarding = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find onboarding progress for id - " + theId);
        }

        return theOnboarding;
    }

    @Override
    public void save(Onboarding theOnboarding) {
        onboardingRepo.save(theOnboarding);
    }

    @Override
    public void deleteById(int theId) {
        onboardingRepo.deleteById(theId);
    }
}
