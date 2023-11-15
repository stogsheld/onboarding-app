package capstone.csc8429.onboardingapp.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/*
    REST controller to handle any home related mappings
*/

@Controller
public class LoginRestController {


    // Directing user to login page
    @GetMapping("/login")
    public String showLogin() {

        // return login page
        return "login";
    }

    // Giving user 'access denied' error if they aren't authorised to access page
    @GetMapping("/access-denied")
    public String showAccessDenied() {

        // return access denied page
        return "access-denied";
    }
}
