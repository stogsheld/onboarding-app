package capstone.csc8429.onboardingapp.rest;

import capstone.csc8429.onboardingapp.entity.Course;
import capstone.csc8429.onboardingapp.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseRestController {

    private CourseService courseService;

    public CourseRestController(CourseService theCourseService) {
        courseService = theCourseService;
    }

    @GetMapping("/list")
    public String listCourses(Model theModel) {

        List<Course> theCourses = courseService.findAll();

        theModel.addAttribute("courses", theCourses);

        return "courses/list-courses";
    }

    @GetMapping("/viewCourse")
    public String viewCourse(@RequestParam("courseId") int theId, Model theModel) {

        // get the employee from the service
        Course theCourse = courseService.findById(theId);

        // set employee in the model to prepopulate the form
        theModel.addAttribute("course", theCourse);

        // send over to our form
        return "courses/view-course";
    }
}
