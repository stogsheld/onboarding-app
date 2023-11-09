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


// REST Controller for course related requests
@Controller
@RequestMapping("/courses")
public class CourseRestController {

    private CourseService courseService;

    private QuestionService questionService;

    private CompletionService completionService;


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

        List<Course> theCourses = courseService.findAll();

        theModel.addAttribute("courses", theCourses);

        return "courses/list-courses";
    }


    // Access individual course view
    @GetMapping("/viewCourse")
    public String viewCourse(@RequestParam("courseId") int theId, Model theModel) {

        // get the course from the service
        Course theCourse = courseService.findById(theId);

        // set course in the model to prepopulate the form
        theModel.addAttribute("course", theCourse);

        // send over to our form
        return "courses/view-course";
    }


    @GetMapping("/startCourse")
    public String startCourse(@RequestParam("courseId") int theId, Model theModel) {

        // get the course from the service
        Course theCourse = courseService.findById(theId);

        // set course in the model to prepopulate the form
        theModel.addAttribute("course", theCourse);

        return "courses/start-course";
    }


    @GetMapping("/viewQuiz")
    public String viewQuiz(@RequestParam("courseId") int theId, Model theModel) {


        Course theCourse = courseService.findById(theId);

        System.out.println(theCourse);

        // set course in the model to prepopulate the form
        theModel.addAttribute("course", theCourse);

        return "courses/view-quiz";
    }


    @GetMapping("/quiz")
    public String startQuiz(@RequestParam("courseId") int theId, Model theModel) {

        // Get all questions for the quiz
        List<Question> question = questionService.findAllByCourseId(theId);

        Answers answers = new Answers();

        theModel.addAttribute("courseId", theId);
        theModel.addAttribute("question", question);
        theModel.addAttribute("answers", answers);

        return "courses/start-quiz";
    }


    // Setting of refresherDays (days before course refresher is needed) from application.properties
    @Value("${refresherDays}")
    private int refresherDays;

    // Setting of course pass mark from application.properties
    @Value("${coursePassMark}")
    private double coursePassMark;

    @PostMapping("processQuizAnswers")
    public String saveQuizAttempt(@ModelAttribute("answers") Answers answers,
                                  @ModelAttribute("courseId") int theId,
                                  Model theModel) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        List<Question> questions = questionService.findAllByCourseId(theId);

        int totalScore = 0;
        List<Integer> answerList = answers.getResponses();


        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getCorrectAnswer() == answerList.get(i)) {
                totalScore++;
            }
        }
        System.out.println("Total score is " + totalScore + ", saving to database...");


        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        java.sql.Date refresherDate = Date.valueOf(date.toLocalDate().plusDays(refresherDays));

        System.out.println("Attempt Date: " + date);
        System.out.println("Refresher Date (if applicable): " + refresherDate);

        // Get the pass mark as a percentage
        double courseMark = (double) (totalScore / questions.size()) * 100;

        String passOrFail;

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

        theModel.addAttribute("passOrFail", passOrFail);

        return "/courses/finish-quiz";
    }
}
