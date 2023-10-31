package capstone.csc8429.onboardingapp.rest;

import capstone.csc8429.onboardingapp.entity.Course;
import capstone.csc8429.onboardingapp.entity.Question;
import capstone.csc8429.onboardingapp.service.CourseService;
import capstone.csc8429.onboardingapp.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// REST Controller for course related requests
@Controller
@RequestMapping("/courses")
public class CourseRestController {

    private CourseService courseService;

    private QuestionService questionService;

    public CourseRestController(CourseService theCourseService, QuestionService theQuestionService) {
        courseService = theCourseService;
        questionService = theQuestionService;
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


        List<Question> questions = questionService.findQuestionsByCourseId(theId);

        System.out.println(questions);

        // set course in the model to prepopulate the form
        theModel.addAttribute("questions", questions);

        return "courses/view-quiz";
    }

}
