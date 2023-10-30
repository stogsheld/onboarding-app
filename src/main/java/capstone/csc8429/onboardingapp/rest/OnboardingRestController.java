package capstone.csc8429.onboardingapp.rest;

import capstone.csc8429.onboardingapp.entity.Onboarding;
import capstone.csc8429.onboardingapp.service.OnboardingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


// REST Controller for onboarding related requests
@Controller
@RequestMapping("/onboarding")
public class OnboardingRestController {

    private OnboardingService onboardingService;

    public OnboardingRestController(OnboardingService theOnboardingService) {
        onboardingService = theOnboardingService;
    }


    // Navigation to the onboarding landing page
    @GetMapping("/onboarding-home")
    public String onboardingLandingPage(@RequestParam("userId") int theId, Model theModel) {

        Onboarding theOnboarding = onboardingService.findById(theId);

        theModel.addAttribute("onboarding_progress", theOnboarding);

        // returning the onboarding-home html file
        return "onboarding/onboarding-home";
    }

    // Navigation to the onboarding setup page
    @GetMapping("/onboarding-setup")
    public String onboardingSetupPage() {

        // returning the onboarding-home html file
        return "onboarding/onboarding-setup";
    }

    // Navigation to the onboarding 'meet the team' page
    @GetMapping("/onboarding-meet-the-team")
    public String onboardingMeetTheTeam() {

        // returning the onboarding-home html file
        return "onboarding/onboarding-meet-the-team";
    }
}
