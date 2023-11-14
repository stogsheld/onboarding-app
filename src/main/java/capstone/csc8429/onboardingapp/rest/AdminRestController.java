package capstone.csc8429.onboardingapp.rest;

import capstone.csc8429.onboardingapp.entity.*;
import capstone.csc8429.onboardingapp.service.*;
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
    private QuestionService questionService;

    public AdminRestController(CourseService courseService, UserService userService,
                               RoleService roleService, OnboardingService onboardingService,
                               QuestionService questionService) {
        this.courseService = courseService;
        this.userService = userService;
        this.roleService = roleService;
        this.onboardingService = onboardingService;
        this.questionService = questionService;
    }

    // Setting up password encoder
    public PasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/admin-home")
    public String getAdmin() {

        return "/admin/admin-home";
    }

    @GetMapping("/adminUsers")
    public String getAdminUsers(Model theModel) {

        List<User> userList = userService.findAll();

        theModel.addAttribute("users", userList);

        return "/admin/admin-users";
    }

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


            theModel.addAttribute("password", password);
        } else {
            theModel.addAttribute("password", "has not been changed.");
        }

        userService.save(user);

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


    @GetMapping("/adminCourses")
    public String getAdminCourses(Model theModel) {

        List<Course> courseList = courseService.findAll();

        theModel.addAttribute("courses", courseList);

        return "admin/admin-courses";
    }

    @GetMapping("/addCourse")
    public String addCourse(Model theModel) {

        Course course = new Course();

        theModel.addAttribute("course", course);

        return "admin/admin-add-course";
    }

    @PostMapping("/processAddCourse")
    public String processAddCourse(@ModelAttribute("course") Course course, Model theModel) {
        courseService.save(course);

        Question question = new Question();

        theModel.addAttribute("question", question);
        theModel.addAttribute("courseId", course.getCourseId());

        return "admin/admin-add-question";
    }

    @GetMapping("/addQuestion")
    public String addQuestion(@RequestParam("courseId") int id, Model theModel) {

        Question question = new Question();
        theModel.addAttribute("question", question);
        theModel.addAttribute("courseId", id);

        return "admin/admin-add-question";
    }

    @PostMapping("/processAddQuestion")
    public String processAddQuestion(@ModelAttribute("question") Question question,
                                     @ModelAttribute("courseId") int courseId, Model theModel) {

        Course course = courseService.findById(courseId);

        question.setCourseId(courseId);
        question.setQuestionNumber(questionService.findAllByCourseId(courseId).size() + 1);

        questionService.save(question);

        theModel.addAttribute("course", course);
        theModel.addAttribute("addedQuestion", "Question added successfully.");

        return "admin/admin-add-question";
    }

    @GetMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam("questionId") int questionId, Model theModel) {

        // Delete user from ALL tables
        int courseId = questionService.findById(questionId).getCourseId();
        questionService.deleteById(questionId);

        theModel.addAttribute("courseId", courseId);

        return "redirect:admin/adminCourses";
    }

    @GetMapping("/viewCourse")
    public String viewCourse(@RequestParam("courseId") int id, Model theModel) {
        Course course = courseService.findById(id);
        List<Question> questions = questionService.findAllByCourseId(id);

        theModel.addAttribute("course", course);
        theModel.addAttribute("questions", questions);

        return "admin/admin-view-course";
    }

    @GetMapping("/updateCourse")
    public String updateCourse(@RequestParam("courseId") int id, Model theModel) {

        Course course = courseService.findById(id);

        theModel.addAttribute("course", course);

        return "admin/admin-update-course";
    }

    @PostMapping("/processUpdateCourse")
    public String processUpdateCourse(@ModelAttribute("course") Course course, Model theModel) {

        // Add the data to the table
        courseService.save(course);

        theModel.addAttribute("course", course);

        //Return to the list users page
        return "admin/admin-course-processed";
    }

    @GetMapping("/deleteCourse")
    public String deleteCourse(@RequestParam("courseId") int id) {

        // Delete user from ALL tables
        questionService.deleteByCourseId(id);
        courseService.deleteById(id);

        return "redirect:admin/adminCourses";
    }







    // TODO - CRUD for courses
}
