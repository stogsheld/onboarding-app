package capstone.csc8429.onboardingapp.rest;

import capstone.csc8429.onboardingapp.entity.User;
import capstone.csc8429.onboardingapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


// REST Controller for home related requests
@Controller
@RequestMapping("/home")
public class HomeRestController {

    private UserService userService;

    public HomeRestController(UserService userService) {
        this.userService = userService;
    }

    // Access the welcome page
    @GetMapping("/welcome")
    public String homePage(Model theModel) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());
        User theUser = userService.findById(userId);

        theModel.addAttribute("theDate", new java.util.Date());
        theModel.addAttribute("theUser", theUser);

        return "home/welcome";
    }
}
