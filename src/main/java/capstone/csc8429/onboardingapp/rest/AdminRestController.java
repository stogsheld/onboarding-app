package capstone.csc8429.onboardingapp.rest;

import capstone.csc8429.onboardingapp.entity.Course;
import capstone.csc8429.onboardingapp.entity.User;
import capstone.csc8429.onboardingapp.service.CourseService;
import capstone.csc8429.onboardingapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


// REST Controller for admin related requests
@Controller
@RequestMapping("/admin")
public class AdminRestController {

    private CourseService courseService;
    private UserService userService;

    public AdminRestController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

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

    // TODO - CRUD for admins
}
