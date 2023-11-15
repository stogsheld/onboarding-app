package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.dao.CourseRepo;
import capstone.csc8429.onboardingapp.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


// Course Service Implementation so that the app can implement JPARepository DB methods in CourseRepo
@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepo courseRepo;

    @Autowired
    public CourseServiceImpl(CourseRepo theCourseRepo) {
        courseRepo = theCourseRepo;
    }

    @Override
    public List<Course> findAll() {
        return courseRepo.findAll();
    }

    @Override
    public Course findById(int theId) {
        Optional<Course> result = courseRepo.findById(theId);

        Course theCourse = null;

        if (result.isPresent()) {
            theCourse = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find course id - " + theId);
        }

        return theCourse;
    }

    @Override
    public void save(Course theCourse) {
        courseRepo.save(theCourse);
    }

    @Override
    public void deleteById(int theId) {
        courseRepo.deleteById(theId);
    }
}
