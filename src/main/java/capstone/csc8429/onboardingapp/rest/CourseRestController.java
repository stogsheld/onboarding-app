package capstone.csc8429.onboardingapp.rest;

import capstone.csc8429.onboardingapp.entity.*;
import capstone.csc8429.onboardingapp.service.CompletionService;
import capstone.csc8429.onboardingapp.service.CourseService;
import capstone.csc8429.onboardingapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;


/*
    REST controller to handle any admin related mappings
*/

@Controller
@RequestMapping("/courses")
public class CourseRestController {

    // Setting up various services so that the Course controller can access the DB
    private CourseService courseService;

    private QuestionService questionService;

    private CompletionService completionService;

    // Constructor for services
    public CourseRestController(CourseService theCourseService,
                                QuestionService theQuestionService,
                                CompletionService theCompletionService) {
        courseService = theCourseService;
        questionService = theQuestionService;
        completionService = theCompletionService;

    }

    // Access the list of courses
    @GetMapping("/list")
    public String listCourses(Model theModel) {

        // Find all courses
        List<Course> theCourses = courseService.findAll();

        // Add courses to the model
        theModel.addAttribute("courses", theCourses);

        // Return the course list page
        return "courses/list-courses";
    }

    // Access individual course view
    @GetMapping("/viewCourse")
    public String viewCourse(@RequestParam("courseId") int theId, Model theModel) {

        // Find course by course ID
        Course theCourse = courseService.findById(theId);

        // Add course to the model
        theModel.addAttribute("course", theCourse);

        // Return the course view page
        return "courses/view-course";
    }

    // Start a course
    @GetMapping("/startCourse")
    public String startCourse(@RequestParam("courseId") int theId, Model theModel) {

        // Find course by course ID
        Course theCourse = courseService.findById(theId);

        // Add course to the model
        theModel.addAttribute("course", theCourse);

        // Return the course start page
        return "courses/start-course";
    }

    // View a course quiz
    @GetMapping("/viewQuiz")
    public String viewQuiz(@RequestParam("courseId") int theId, Model theModel) {

        // Find course by course ID
        Course theCourse = courseService.findById(theId);

        // Add course to the model
        theModel.addAttribute("course", theCourse);

        // Return the course quiz page
        return "courses/view-quiz";
    }

    // Take a course quiz
    @GetMapping("/quiz")
    public String startQuiz(@RequestParam("courseId") int theId, Model theModel) {

        // Get all questions for the quiz
        List<Question> question = questionService.findAllByCourseId(theId);

        // Generate a new empty answer (for answers on the quiz to be recorded)
        Answers answers = new Answers();

        // Add course ID, course questions and empty answer object to the model
        theModel.addAttribute("courseId", theId);
        theModel.addAttribute("question", question);
        theModel.addAttribute("answers", answers);

        // Return the 'start quiz' page
        return "courses/start-quiz";
    }

    // Setting of refresherDays (days before course refresher is needed) from application.properties
    @Value("${refresherDays}")
    private int refresherDays;

    // Setting of course pass mark from application.properties
    @Value("${coursePassMark}")
    private double coursePassMark;

    // Processing quiz answers
    @PostMapping("processQuizAnswers")
    public String saveQuizAttempt(@ModelAttribute("answers") Answers answers,
                                  @ModelAttribute("courseId") int theId,
                                  Model theModel) {

        // Get user ID from authentication (checks currently logged-in user)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        // Get list of questions for the course ID
        List<Question> questions = questionService.findAllByCourseId(theId);

        // Set up total score as 0
        int totalScore = 0;

        // Get list of user submitted answers from the quiz
        List<Integer> answerList = answers.getResponses();

        // Increment 'total score' variable whenever user has a correct answer
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getCorrectAnswer() == answerList.get(i)) {
                totalScore++;
            }
        }

        // Sense check in console to check total score
        System.out.println("Total score is " + totalScore + ", saving to database...");

        // Get current date
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());

        // Get refresher date
        java.sql.Date refresherDate = Date.valueOf(date.toLocalDate().plusDays(refresherDays));

        // Sense checks in console to check current date and refresher date
        System.out.println("Attempt Date: " + date);
        System.out.println("Refresher Date (if applicable): " + refresherDate);

        // Get the pass mark as a percentage
        double courseMark = (double) (totalScore / questions.size()) * 100;

        // Set up String to display if user has passed or failed
        String passOrFail;

        // Generate database row based on whether user has passed or failed the course
        if (courseMark < coursePassMark) {
            Completion completion = new Completion(userId, theId, totalScore,
                    date);
            passOrFail = "Fail";
            completionService.save(completion);
        } else {
            Completion completion = new Completion(userId, theId, totalScore, date, date, refresherDate);
            passOrFail = "Pass";
            completionService.save(completion);
        }

        // Add pass/fail string to the model
        theModel.addAttribute("passOrFail", passOrFail);

        // Return 'finish quiz' page
        return "/courses/finish-quiz";
    }
}
