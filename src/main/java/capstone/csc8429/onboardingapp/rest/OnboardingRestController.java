package capstone.csc8429.onboardingapp.rest;

import capstone.csc8429.onboardingapp.entity.Onboarding;
import capstone.csc8429.onboardingapp.service.OnboardingService;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String onboardingLandingPage(Model theModel) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        // Look for onboarding
        Onboarding theOnboarding = onboardingService.findById(userId);

        // If no onboarding exists, create one
        if (theOnboarding == null) {
            theOnboarding = new Onboarding(userId, 0, 0, 0, 0);
            onboardingService.save(theOnboarding);
        }

        theModel.addAttribute("onboarding_progress", theOnboarding);

        // returning the onboarding-home html file
        return "onboarding/onboarding-home";
    }

    // Navigation to the onboarding setup page
    @GetMapping("/onboarding-setup")
    public String onboardingSetupPage() {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(3, 1);

        // returning the onboarding-home html file
        return "onboarding/onboarding-setup";
    }

    // Navigation to the onboarding 'meet the team' page
    @GetMapping("/onboarding-meet-the-team")
    public String onboardingMeetTheTeam() {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(4, 1);

        // returning the onboarding-home html file
        return "onboarding/onboarding-meet-the-team";
    }


    // Setting the user's onboarding progress based on the page they are accessing,
    // and whether the onboarding is 'in progress' or 'complete'
    public void setOnboardingProgress(int updateOption, int completionSetting) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        // Look for onboarding
        Onboarding theOnboarding = onboardingService.findById(userId);

        int currentStatus;

        switch(updateOption) {
            case 1:
                currentStatus = theOnboarding.getWelcomeCompletion();
                if (currentStatus == 2) {
                    break;
                }
                theOnboarding.setWelcomeCompletion(completionSetting);
                break;
            case 2:
                currentStatus = theOnboarding.getDataEthicsCompletion();
                if (currentStatus == 2) {
                    break;
                }
                theOnboarding.setDataEthicsCompletion(completionSetting);
                break;
            case 3:
                currentStatus = theOnboarding.getSetupCompletion();
                if (currentStatus == 2) {
                    break;
                }
                theOnboarding.setSetupCompletion(completionSetting);
                break;
            case 4:
                currentStatus = theOnboarding.getMeetTeamCompletion();
                if (currentStatus == 2) {
                    break;
                }
                theOnboarding.setMeetTeamCompletion(completionSetting);
                break;
            default:
                throw new RuntimeException("Invalid argument");
        }
        onboardingService.save(theOnboarding);
    }
}
