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
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateOutputException;

import java.text.SimpleDateFormat;
import java.util.*;


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

    @PostMapping("/processAddUser")
    public String processAddUser(@ModelAttribute("user") User user, Model theModel) {

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
        theModel.addAttribute("addedOrUpdated", "added");

        //Return to the list users page
        return "/admin/admin-user-processed";
    }


    @GetMapping("/viewUser")
    public String viewUser(@RequestParam("userId") int id, Model theModel) {
        User user = userService.findById(id);

        theModel.addAttribute("user", user);

        return "/admin/admin-view-user";
    }


    @GetMapping("/updateUser")
    public String updateUser(@RequestParam("userId") int id, Model theModel) {

        User user = userService.findById(id);

        theModel.addAttribute("user", user);

        return "/admin/admin-update-user";
    }

    @PostMapping("/processUpdateUser")
    public String processUpdateUser(@ModelAttribute("user") User user, Model theModel) {

        if (Objects.equals(user.getPassword(), "Yes")) {

            // Generate a random password for the user -
            // Taken from https://stackoverflow.com/questions/19743124/java-password-generator
            String password = new Random().ints(10, 33, 122).
                    collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            // Use bCrypt to encrypt the password
            user.setPassword("{bcrypt}" + encoder.encode(password));

            // Add the data to the table
            userService.save(user);

            theModel.addAttribute("password", password);
        } else {
            theModel.addAttribute("password", "has not been changed.");
        }

        theModel.addAttribute("addedOrUpdated", "updated");
        theModel.addAttribute("user", user);


        //Return to the list users page
        return "/admin/admin-user-processed";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") int id) {

        // Delete user from ALL tables
        userService.deleteById(id);
        onboardingService.deleteById(id);
        roleService.deleteById(id);

        return "redirect:/admin/adminUsers";
    }




    // TODO - CRUD for courses
}
