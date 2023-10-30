package capstone.csc8429.onboardingapp.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/onboarding")
public class OnboardingRestController {

    // Navigation to the onboarding landing page
    @GetMapping("/onboarding-home")
    public String onboardingLandingPage() {

        // returning the onboarding-home html file
        return "onboarding/onboarding-home";
    }

    // Navigation to the onboarding setup page
    @GetMapping("/onboarding-setup")
    public String onboardingSetupPage() {

        // returning the onboarding-home html file
        return "onboarding/onboarding-setup";
    }

    // Navigation to the onboarding setup page
    @GetMapping("/onboarding-meet-the-team")
    public String onboardingMeetTheTeam() {

        // returning the onboarding-home html file
        return "onboarding/onboarding-meet-the-team";
    }
}
