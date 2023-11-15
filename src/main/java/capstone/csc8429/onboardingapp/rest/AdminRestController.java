package capstone.csc8429.onboardingapp.rest;

import capstone.csc8429.onboardingapp.entity.*;
import capstone.csc8429.onboardingapp.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Random;


/*
    REST controller to handle any admin related mappings
*/

@Controller
@RequestMapping("/admin")
public class AdminRestController {

    // Setting up various services so that the Admin controller can access the DB
    private CourseService courseService;
    private UserService userService;
    private RoleService roleService;
    private OnboardingService onboardingService;
    private QuestionService questionService;

    // Constructor for services
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


    // Accessing the admin home page
    @GetMapping("/adminHome")
    public String getAdmin() {

        // Display Admin Homepage
        return "/admin/admin-home";
    }


    // USER RELATED MAPPINGS
    // Display user list
    @GetMapping("/adminUsers")
    public String getAdminUsers(Model theModel) {

        // Retrieve all users
        List<User> userList = userService.findAll();

        // Add all users to the model
        theModel.addAttribute("users", userList);

        // Display Admin Users List
        return "/admin/admin-users";
    }

    // Add a user
    @GetMapping("/addUser")
    public String addUser(Model theModel) {

        // Create new user (empty as form will add the details)
        User user = new User();

        // Add user to the model
        theModel.addAttribute("user", user);

        // Display form to add user
        return "/admin/admin-add-user";
    }

    // Setting value for admin level (i.e. users with level of 5 or lower will get admin when created)
    @Value("${adminLevel}")
    private int adminLevel;

    // Processing add user form
    @PostMapping("/processAddUser")
    public String processAddUser(@ModelAttribute("user") User user, Model theModel) {

        // Generate a random password for the user -
        // Taken from https://stackoverflow.com/questions/19743124/java-password-generator
        String password = new Random().ints(10, 33, 122).
                collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        // Use bCrypt to encrypt the password
        user.setPassword("{bcrypt}" + encoder.encode(password));

        // Setting user as active user
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

        // Add user, plaintext password and 'added' to the model
        theModel.addAttribute("user", user);
        theModel.addAttribute("password", password);
        theModel.addAttribute("addedOrUpdated", "added");

        //Return to the list users page
        return "/admin/admin-user-processed";
    }

    // View a user
    @GetMapping("/viewUser")
    public String viewUser(@RequestParam("userId") int id, Model theModel) {

        // Retrieve user from the DB
        User user = userService.findById(id);

        // Add user to the model
        theModel.addAttribute("user", user);

        return "/admin/admin-view-user";
    }

    // Update a user
    @GetMapping("/updateUser")
    public String updateUser(@RequestParam("userId") int id, Model theModel) {

        // Retrieve user from the DB
        User user = userService.findById(id);

        // Add user to the model
        theModel.addAttribute("user", user);

        return "/admin/admin-update-user";
    }

    // Processing update user form
    @PostMapping("/processUpdateUser")
    public String processUpdateUser(@ModelAttribute("user") User user, Model theModel) {

        // If a new password has been requested, generate new password
        if (Objects.equals(user.getPassword(), "Yes")) {

            // Generate a random password for the user -
            // Taken from https://stackoverflow.com/questions/19743124/java-password-generator
            String password = new Random().ints(10, 33, 122).
                    collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            // Use bCrypt to encrypt the password
            user.setPassword("{bcrypt}" + encoder.encode(password));

            // Add plaintext password to the model
            theModel.addAttribute("password", password);
        } else {

            // Display a 'password not changed' message in the model
            theModel.addAttribute("password", "has not been changed.");
        }

        // Add the data to the table
        userService.save(user);

        // Add user and 'updated' message to the model
        theModel.addAttribute("user", user);
        theModel.addAttribute("addedOrUpdated", "updated");

        //Return to the list users page
        return "/admin/admin-user-processed";
    }

    // Processing delete user
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") int id) {

        // Delete user from ALL tables
        userService.deleteById(id);
        onboardingService.deleteById(id);
        roleService.deleteById(id);

        // Return to the users list page
        return "redirect:/admin/adminUsers";
    }


    // COURSE RELATED MAPPINGS
    // Display course list
    @GetMapping("/adminCourses")
    public String getAdminCourses(Model theModel) {

        // Retrieve all courses from the DB
        List<Course> courseList = courseService.findAll();

        // Add the courses to the model
        theModel.addAttribute("courses", courseList);

        // Return Admin Course list
        return "admin/admin-courses";
    }

    // Add a course
    @GetMapping("/addCourse")
    public String addCourse(Model theModel) {

        // Create empty course (to be filled in by user form)
        Course course = new Course();

        // Add course to the model
        theModel.addAttribute("course", course);

        // Display add course form
        return "admin/admin-add-course";
    }

    // Process add course form
    @PostMapping("/processAddCourse")
    public String processAddCourse(@ModelAttribute("course") Course course, Model theModel) {

        // Save course to the DB
        courseService.save(course);

        // Add empty question (to be filled in by the question form)
        Question question = new Question();

        // Add question and course ID to the model
        theModel.addAttribute("question", question);
        theModel.addAttribute("courseId", course.getCourseId());

        // Display add question form
        return "admin/admin-add-question";
    }

    // View a course
    @GetMapping("/viewCourse")
    public String viewCourse(@RequestParam("courseId") int id, Model theModel) {

        // Find course details by ID
        Course course = courseService.findById(id);

        // List all course questions by course ID
        List<Question> questions = questionService.findAllByCourseId(id);

        // Add course and questions to the model
        theModel.addAttribute("course", course);
        theModel.addAttribute("questions", questions);

        // Return view course page
        return "admin/admin-view-course";
    }

    // Update a course
    @GetMapping("/updateCourse")
    public String updateCourse(@RequestParam("courseId") int id, Model theModel) {

        // Retrieve course by course ID
        Course course = courseService.findById(id);

        // Add course to the model
        theModel.addAttribute("course", course);

        // Display update course page
        return "admin/admin-update-course";
    }

    // Process update course form
    @PostMapping("/processUpdateCourse")
    public String processUpdateCourse(@ModelAttribute("course") Course course, Model theModel) {

        // Add the data to the table
        courseService.save(course);

        // Add course to the model
        theModel.addAttribute("course", course);

        //Return to the list users page
        return "admin/admin-course-processed";
    }

    // Delete a course
    @GetMapping("/deleteCourse")
    public String deleteCourse(@RequestParam("courseId") int id) {

        // Delete user from ALL tables
        questionService.deleteByCourseId(id);
        courseService.deleteById(id);

        // Return to the course list page
        return "redirect:admin/adminCourses";
    }


    // QUESTION RELATED MAPPINGS
    // Add a question
    @GetMapping("/addQuestion")
    public String addQuestion(@RequestParam("courseId") int id, Model theModel) {

        // Add empty question (to be filled in by the question form)
        Question question = new Question();

        // Add question and course ID to the model
        theModel.addAttribute("question", question);
        theModel.addAttribute("courseId", id);

        // Display add question form
        return "admin/admin-add-question";
    }

    // Process add question form
    @PostMapping("/processAddQuestion")
    public String processAddQuestion(@ModelAttribute("question") Question question,
                                     @ModelAttribute("courseId") int courseId, Model theModel) {

        // Find the course that the question relates to in the DB
        Course course = courseService.findById(courseId);

        // Set question's course ID as course ID
        question.setCourseId(courseId);

        // Set the Question number as the current sum of questions + 1
        question.setQuestionNumber(questionService.findAllByCourseId(courseId).size() + 1);

        // Save the question
        questionService.save(question);

        // Add course and question message to the model
        theModel.addAttribute("course", course);
        theModel.addAttribute("addedQuestion", "Question added successfully.");

        // Return 'question added' page
        return "admin/admin-add-question";
    }

    // Delete a question
    @GetMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam("questionId") int questionId, Model theModel) {

        // Delete user from ALL tables
        int courseId = questionService.findById(questionId).getCourseId();
        questionService.deleteById(questionId);

        // Add course ID to the model
        theModel.addAttribute("courseId", courseId);

        // Return course list
        return "redirect:admin/adminCourses";
    }

}
