package capstone.csc8429.onboardingapp.rest;

import capstone.csc8429.onboardingapp.entity.Course;
import capstone.csc8429.onboardingapp.entity.Onboarding;
import capstone.csc8429.onboardingapp.entity.Role;
import capstone.csc8429.onboardingapp.entity.User;
import capstone.csc8429.onboardingapp.service.CourseService;
import capstone.csc8429.onboardingapp.service.OnboardingService;
import capstone.csc8429.onboardingapp.service.RoleService;
import capstone.csc8429.onboardingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


// REST Controller for admin related requests
@Controller
@RequestMapping("/admin")
public class AdminRestController {

    private CourseService courseService;
    private UserService userService;
    private RoleService roleService;
    private OnboardingService onboardingService;

    public AdminRestController(CourseService courseService, UserService userService,
                               RoleService roleService, OnboardingService onboardingService) {
        this.courseService = courseService;
        this.userService = userService;
        this.roleService = roleService;
        this.onboardingService = onboardingService;
    }

    // Setting up password encoder
    public PasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/admin-home")
    public String getAdmin() {

        return "/admin/admin-home";
    }

    @GetMapping("/adminCourses")
    public String getAdminCourses(Model theModel) {

        List<Course> courseList = courseService.findAll();

        theModel.addAttribute("courses", courseList);

        return "/admin/admin-courses";
    }

    @GetMapping("/adminUsers")
    public String getAdminUsers(Model theModel) {

        List<User> userList = userService.findAll();

        theModel.addAttribute("users", userList);

        return "/admin/admin-users";
    }

    // TODO - CRUD for users

    @GetMapping("/addUser")
    public String addUser(Model theModel) {

        User user = new User();

        theModel.addAttribute("user", user);

        return "/admin/admin-add-user";
    }


    @Value("${adminLevel}")
    private int adminLevel;

    @PostMapping("/processUser")
    public String processUser(@ModelAttribute("user") User user, Model theModel) {

        // Generate a random password for the user -
        // Taken from https://stackoverflow.com/questions/19743124/java-password-generator
        String password = new Random().ints(10, 33, 122).
                collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        // Use bCrypt to encrypt the password
        user.setPassword("{bcrypt}" + encoder.encode(password));

        user.setActive(true);

        // Add the data to the table
        userService.save(user);

        // Add user permissions
        Role employeeRole = new Role(user.getUserId(), "ROLE_EMPLOYEE");
        roleService.save(employeeRole);

        // Check if user is eligible for admin privileges
        if (Integer.parseInt(user.getCareerLevel()) <= adminLevel) {
            Role adminRole = new Role(user.getUserId(), "ROLE_ADMIN");
            roleService.save(adminRole);
        }

        // Create onboarding for the user
        Onboarding theOnboarding = new Onboarding(user.getUserId(), 0, 0, 0, 0);
        onboardingService.save(theOnboarding);

        theModel.addAttribute("user", user);
        theModel.addAttribute("password", password);

        //Return to the list users page
        return "/admin/admin-user-processed";
    }

    // TODO - CRUD for courses
}
