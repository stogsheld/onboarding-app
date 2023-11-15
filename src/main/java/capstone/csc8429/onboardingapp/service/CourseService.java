package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.entity.Course;

import java.util.List;


// Course Service so that the app can implement JPARepository DB methods in CourseRepo
public interface CourseService {

    List<Course> findAll();

    Course findById(int theId);

    void save(Course theCourse);

    void deleteById(int theId);

}
