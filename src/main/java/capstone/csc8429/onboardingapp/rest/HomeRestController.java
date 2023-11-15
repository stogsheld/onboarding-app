package capstone.csc8429.onboardingapp.rest;

import capstone.csc8429.onboardingapp.entity.User;
import capstone.csc8429.onboardingapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/*
    REST controller to handle any home related mappings
*/

@Controller
@RequestMapping("/home")
public class HomeRestController {

    // Setting up user service so that home controller can access the User DB
    private UserService userService;

    // Constructor for user service
    public HomeRestController(UserService userService) {
        this.userService = userService;
    }


    // Access the welcome page
    @GetMapping("/welcome")
    public String homePage(Model theModel) {

        // Get user ID from authentication service (currently logged-in user)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());
        User theUser = userService.findById(userId);

        // Add user details to the model
        theModel.addAttribute("theUser", theUser);

        // Return welcome page
        return "home/welcome";
    }
}
