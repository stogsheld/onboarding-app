package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.entity.Course;

import java.util.List;

public interface CourseService {

    List<Course> findAll();

    Course findById(int theId);

}
