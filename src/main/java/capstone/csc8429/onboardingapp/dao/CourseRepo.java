package capstone.csc8429.onboardingapp.dao;

import capstone.csc8429.onboardingapp.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course, Integer> {


}
