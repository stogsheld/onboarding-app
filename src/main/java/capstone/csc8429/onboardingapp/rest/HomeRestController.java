package capstone.csc8429.onboardingapp.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


// REST Controller for home related requests
@Controller
@RequestMapping("/home")
public class HomeRestController {


    // Access the welcome page
    @GetMapping("/welcome")
    public String homePage(Model theModel) {

        theModel.addAttribute("theDate", new java.util.Date());

        return "home/welcome";
    }
}
