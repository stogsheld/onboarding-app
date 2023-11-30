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


/*
    REST controller to handle any home related mappings
*/

@Controller
@RequestMapping("/onboarding")
public class OnboardingRestController {

    // Setting up various services so that the Onboarding controller can access the DB
    private OnboardingService onboardingService;
    private CompletionService completionService;
    private UserService userService;

    // Constructor for services
    public OnboardingRestController(OnboardingService theOnboardingService,
                                    CompletionService theCompletionService,
                                    UserService theUserService) {
        onboardingService = theOnboardingService;
        completionService = theCompletionService;
        userService = theUserService;
    }


    // Onboarding landing page
    @GetMapping("/onboardingHome")
    public String onboardingHome(Model theModel) {

        // Get user's ID
        int userId = getAuthenticationId();

        // Look for onboarding
        Onboarding theOnboarding = onboardingService.findById(userId);

        // If no onboarding exists, create one
        if (theOnboarding == null) {
            theOnboarding = new Onboarding(userId, 0, 0, 0, 0);
            onboardingService.save(theOnboarding);
        }

        // Work out how far through the onboarding process the user is in percentage format
        double onboardingTotalScore = theOnboarding.getWelcomeCompletion() +
                theOnboarding.getDataEthicsCompletion()
                + theOnboarding.getSetupCompletion() + theOnboarding.getMeetTeamCompletion();
        double onboardingCompletionPercentage = (onboardingTotalScore / 8) * 100;

        // Add onboarding progress to the model
        theModel.addAttribute("onboarding_progress", theOnboarding);
        theModel.addAttribute("onboarding_percentage", onboardingCompletionPercentage);

        // Return the onboarding home page
        return "onboarding/onboarding-home";
    }


    // Onboarding 'welcome to HMRC' page
    @GetMapping("/onboardingWelcome")
    public String onboardingWelcomePage() {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(1, 1);

        // Return the onboarding welcome page
        return "onboarding/onboarding-welcome";
    }

    // Complete onboarding welcome
    @GetMapping("/onboardingCompleteWelcome")
    public String onboardingCompleteWelcome(Model theModel) {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(1, 2);

        // Get user's onboarding progress from the DB
        Onboarding onboarding = onboardingService.findById(getAuthenticationId());

        // Add onboarding progress to the model
        theModel.addAttribute("onboarding_progress", onboarding);

        // Return the onboarding home page
        return "onboarding/onboarding-home";
    }


    // Onboarding 'Data Ethics' page
    @GetMapping("/onboardingDataEthics")
    public String onboardingDataEthicsPage() {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(2, 1);

        // Return the onboarding Data Ethics page
        return "onboarding/onboarding-data-ethics";
    }

    //Complete onboarding Data Ethics
    @GetMapping("/onboardingCompleteDataEthics")
    public String onboardingCompleteDataEthics(Model theModel) {

        // Get user ID
        int userId = getAuthenticationId();

        // Get all completions for current user
        List<Completion> completion = completionService.findByUserId(userId);

        // If user has no completions, then create one for Data Ethics course
        if (completion == null) {
            completion.add(new Completion(userId, 1, 0, null, null, null));
        }

        // Check if there is a date of completion for Course ID #1 (the Data Ethics course)
        // i.e. if course has been passed
        for (int i = 0; i < completion.size(); i++) {
            Completion tempCompletion = completion.get(i);
            if (tempCompletion.getCourseId() == 1) {
                if (tempCompletion.getCompletionDate() != null) {
                    setOnboardingProgress(2, 2);
                }
            }
        }

        // Get new onboarding based on updated values
        Onboarding onboarding = onboardingService.findById(getAuthenticationId());

        // Add onboarding progress to the model
        theModel.addAttribute("onboarding_progress", onboarding);

        // Return onboarding home page
        return "onboarding/onboarding-home";
    }


    // Onboarding setup page
    @GetMapping("/onboardingSetup")
    public String onboardingSetupPage() {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(3, 1);

        // returning the onboarding-home html file
        return "onboarding/onboarding-setup";
    }

    // Complete onboarding setup
    @GetMapping("/onboardingCompleteSetup")
    public String onboardingCompleteSetup(Model theModel) {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(3, 2);

        // Get user's onboarding progress
        Onboarding onboarding = onboardingService.findById(getAuthenticationId());

        // Add onboarding progress to the model
        theModel.addAttribute("onboarding_progress", onboarding);

        // Return onboarding home page
        return "onboarding/onboarding-home";
    }


    // Onboarding 'Meet the Team' page
    @GetMapping("/onboardingMeetTheTeam")
    public String onboardingMeetTheTeam(Model theModel) {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(4, 1);

        // Get user ID and user's Team ID
        int userId = getAuthenticationId();
        String teamId = userService.findById(userId).getTeamId();

        // Get list of all users in the current user's team
        List<User> users = userService.findByTeamId(teamId);

        // Add other team members to the model
        theModel.addAttribute("users", users);

        // Return onboarding 'Meet the Team' page
        return "onboarding/onboarding-meet-the-team";
    }

    // Complete 'Meet the Team' onboarding
    @GetMapping("/onboardingCompleteMeetTheTeam")
    public String onboardingCompleteMeetTheTeam(Model theModel) {

        // Set user's onboarding progress to 1 if it's 0 (i.e. 'start' the course if not already started/completed)
        setOnboardingProgress(4, 2);

        // Get user's onboarding progress
        Onboarding onboarding = onboardingService.findById(getAuthenticationId());

        // Add user's onboarding progress to the model
        theModel.addAttribute("onboarding_progress", onboarding);

        // returning the onboarding-home html file
        return "onboarding/onboarding-home";
    }


    // Setting the user's onboarding progress based on the page they are accessing,
    // and whether the onboarding is 'in progress' or 'complete'
    public void setOnboardingProgress(int updateOption, int completionSetting) {

        int userId = getAuthenticationId();

        // Retrieve user's onboarding progress
        Onboarding theOnboarding = onboardingService.findById(userId);

        int currentStatus = getOnboardingType(updateOption);

        if (currentStatus == 2) {
            return;
        } else {
            switch(updateOption) {
                case 1:
                    theOnboarding.setWelcomeCompletion(completionSetting);
                    break;
                case 2:
                    theOnboarding.setDataEthicsCompletion(completionSetting);
                    break;
                case 3:
                    theOnboarding.setSetupCompletion(completionSetting);
                    break;
                case 4:
                    theOnboarding.setMeetTeamCompletion(completionSetting);
                    break;
                default:
                    break;
            }
            onboardingService.save(theOnboarding);
        }
    }

    public int getOnboardingType(int updateOption) {

        int userId = getAuthenticationId();

        // Retrieve user's onboarding progress
        Onboarding theOnboarding = onboardingService.findById(userId);

        return switch (updateOption) {
            case 1 -> theOnboarding.getWelcomeCompletion();
            case 2 -> theOnboarding.getDataEthicsCompletion();
            case 3 -> theOnboarding.getSetupCompletion();
            case 4 -> theOnboarding.getMeetTeamCompletion();
            default -> -1;
        };
    }



    // Get users ID based on currently logged-in user
    public int getAuthenticationId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Integer.parseInt(authentication.getName());
    }
}
