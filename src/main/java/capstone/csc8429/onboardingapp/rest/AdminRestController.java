package capstone.csc8429.onboardingapp.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


// REST Controller for admin related requests
@Controller
@RequestMapping("/admin")
public class AdminRestController {

    @GetMapping("/admin-home")
    public String getAdmin() {

        return "/admin/admin-home";
    }
}
