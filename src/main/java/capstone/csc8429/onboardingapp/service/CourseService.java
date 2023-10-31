package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.entity.Course;

import java.util.List;


// Implementing CRUD methods for course_info table
public interface CourseService {

    List<Course> findAll();

    Course findById(int theId);

    void save(Course theCourse);

    void deleteById(int theId);

}
