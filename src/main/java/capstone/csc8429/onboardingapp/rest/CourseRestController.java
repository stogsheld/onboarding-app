package capstone.csc8429.onboardingapp.rest;

import capstone.csc8429.onboardingapp.entity.Attempt;
import capstone.csc8429.onboardingapp.entity.Completion;
import capstone.csc8429.onboardingapp.entity.Course;
import capstone.csc8429.onboardingapp.entity.Question;
import capstone.csc8429.onboardingapp.service.AttemptService;
import capstone.csc8429.onboardingapp.service.CompletionService;
import capstone.csc8429.onboardingapp.service.CourseService;
import capstone.csc8429.onboardingapp.service.QuestionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


// REST Controller for course related requests
@Controller
@RequestMapping("/courses")
public class CourseRestController {

    private CourseService courseService;

    private QuestionService questionService;

    private AttemptService attemptService;

    private CompletionService completionService;


    public CourseRestController(CourseService theCourseService,
                                QuestionService theQuestionService,
                                AttemptService theAttemptService,
                                CompletionService theCompletionService) {
        courseService = theCourseService;
        questionService = theQuestionService;
        attemptService = theAttemptService;
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

        Attempt attempt = new Attempt();

        theModel.addAttribute("courseId", theId);
        theModel.addAttribute("question", question);
        theModel.addAttribute("attempt", attempt);

        return "courses/start-quiz";
    }

    @PostMapping("processQuizAnswers")
    public String saveQuizAttempt(@ModelAttribute("attempt") Attempt theAttempt,
                                  @ModelAttribute("courseId") int theId,
                                  Model theModel) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        theAttempt.setUserId(userId);

        List<Question> questions = questionService.findAllByCourseId(theId);

        int[] scores = {theAttempt.getAnswerOne(), theAttempt.getAnswerTwo(), theAttempt.getAnswerThree(),
                theAttempt.getAnswerFour(), theAttempt.getAnswerFive()};

        int totalScore = 0;

        for (int i = 0; i < 5; i++) {
            if (questions.get(i).getCorrectAnswer() == scores[i]) {
                totalScore++;
            }
        }

        System.out.println("Total score is " + totalScore);

        attemptService.save(theAttempt);

        Completion completion = new Completion(userId, theId, totalScore,
                null, null, null);
        completionService.save(completion);

        theModel.addAttribute("attempt", theAttempt);

        return "/courses/finish-quiz";
    }


}
