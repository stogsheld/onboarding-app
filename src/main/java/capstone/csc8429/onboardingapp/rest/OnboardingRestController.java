package capstone.csc8429.onboardingapp.rest;

import capstone.csc8429.onboardingapp.entity.Completion;
import capstone.csc8429.onboardingapp.entity.Onboarding;
import capstone.csc8429.onboardingapp.entity.User;
import capstone.csc8429.onboardingapp.service.CompletionService;
import capstone.csc8429.onboardingapp.service.CourseService;
import capstone.csc8429.onboardingapp.service.OnboardingService;
import capstone.csc8429.onboardingapp.service.UserService;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;


// REST Controller for onboarding related requests
@Controller
@RequestMapping("/onboarding")
public class OnboardingRestController {

    private OnboardingService onboardingService;
    private CompletionService completionService;
    private UserService userService;

    public OnboardingRestController(OnboardingService theOnboardingService,
                                    CompletionService theCompletionService,
                                    UserService theUserService) {
        onboardingService = theOnboardingService;
        completionService = theCompletionService;
        userService = theUserService;
    }


    // Navigation to the onboarding landing page
    @GetMapping("/onboardingHome")
    public String onboardingHome(Model theModel) {

        int userId = getAuthenticationId();

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

    @GetMapping("/onboardingWelcome")
    public String onboardingWelcomePage() {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(1, 1);

        // returning the onboarding-home html file
        return "onboarding/onboarding-welcome";
    }

    @GetMapping("/onboardingCompleteWelcome")
    public String onboardingCompleteWelcome(Model theModel) {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(1, 2);

        Onboarding onboarding = onboardingService.findById(getAuthenticationId());

        theModel.addAttribute("onboarding_progress", onboarding);

        // returning the onboarding-home html file
        return "onboarding/onboarding-home";
    }

    @GetMapping("/onboardingDataEthics")
    public String onboardingDataEthicsPage() {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(2, 1);

        // returning the onboarding-home html file
        return "onboarding/onboarding-data-ethics";
    }

    @GetMapping("/onboardingCompleteDataEthics")
    public String onboardingCompleteDataEthics(Model theModel) {

        int userId = getAuthenticationId();

        List<Completion> completion = completionService.findByUserId(userId);

        if (completion == null) {
            completion.add(new Completion(userId, 1, 0, null, null, null));
        }

        for (int i = 0; i < completion.size(); i++) {
            Completion tempCompletion = completion.get(i);
            if (tempCompletion.getCourseId() == 1) {
                if (tempCompletion.getCompletionDate() != null) {
                    setOnboardingProgress(2, 2);
                }
            }
        }


        Onboarding onboarding = onboardingService.findById(getAuthenticationId());

        theModel.addAttribute("onboarding_progress", onboarding);

        return "onboarding/onboarding-home";
    }


    // Navigation to the onboarding setup page
    @GetMapping("/onboardingSetup")
    public String onboardingSetupPage() {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(3, 1);

        // returning the onboarding-home html file
        return "onboarding/onboarding-setup";
    }

    @GetMapping("/onboardingCompleteSetup")
    public String onboardingCompleteSetup(Model theModel) {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(3, 2);

        Onboarding onboarding = onboardingService.findById(getAuthenticationId());

        theModel.addAttribute("onboarding_progress", onboarding);

        // returning the onboarding-home html file
        return "onboarding/onboarding-home";
    }



    // Navigation to the onboarding 'meet the team' page
    @GetMapping("/onboardingMeetTheTeam")
    public String onboardingMeetTheTeam(Model theModel) {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(4, 1);

        int userId = getAuthenticationId();
        String teamId = userService.findById(userId).getTeamId();

        List<User> users = userService.findByTeamId(teamId);

        theModel.addAttribute("users", users);

        // returning the onboarding-home html file
        return "onboarding/onboarding-meet-the-team";
    }

    @GetMapping("/onboardingCompleteMeetTheTeam")
    public String onboardingCompleteMeetTheTeam(Model theModel) {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(4, 2);

        Onboarding onboarding = onboardingService.findById(getAuthenticationId());

        theModel.addAttribute("onboarding_progress", onboarding);

        // returning the onboarding-home html file
        return "onboarding/onboarding-home";
    }


    // Setting the user's onboarding progress based on the page they are accessing,
    // and whether the onboarding is 'in progress' or 'complete'
    public void setOnboardingProgress(int updateOption, int completionSetting) {

        int userId = getAuthenticationId();

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

    public int getAuthenticationId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Integer.parseInt(authentication.getName());
    }
}
