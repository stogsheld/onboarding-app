package capstone.csc8429.onboardingapp;

import capstone.csc8429.onboardingapp.dao.CourseRepo;
import capstone.csc8429.onboardingapp.entity.Course;
import capstone.csc8429.onboardingapp.service.CourseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// Testing the Course service
@TestPropertySource("/application.properties")
@SpringBootTest
@ContextConfiguration(classes = {JdbcTemplate.class, CourseService.class, CourseRepo.class})
public class CourseServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepo courseRepo;


    // Setting up a fresh database with the below entry for every test
    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute("insert into course_info(course_id, course_name, course_description, course_content)" +
                "values (4, 'Intro to Security', 'Description Test', 'Content Test')");
    }

    // Testing CourseService ability to create courses
    @Test
    public void createCourseService() {

        Course testCourse = new Course(8, "Advanced Data Storage",
                "Test Data Storage Description", "Test Data Storage Content");
        courseService.save(testCourse);

        Course databaseCourse = courseService.findById(8);

        assertEquals(8, databaseCourse.getCourseId(), "Find by course ID");
    }

    // Testing CourseService ability to read courses
    @Test
    public void readCourseService() {

        Course testCourse = courseService.findById(4);

        assertEquals("Intro to Security", testCourse.getCourseName(), "Read Course Name");
    }

    // Testing CourseService ability to update courses
    @Test
    public void updateCourseService() {

        Course course = courseService.findById(4);

        course.setCourseContent("Updated Course Content Test");
        courseService.save(course);

        Course updatedCourse = courseService.findById(4);

        assertEquals("Updated Course Content Test", updatedCourse.getCourseContent(),
                "Check Updated Course Content");
    }

    // Testing CourseService ability to delete courses
    @Test
    public void deleteCourseService() {

        Optional<Course> deletedCourse = courseRepo.findById(4);

        assertTrue(deletedCourse.isPresent(), "Should Return True");

        courseService.deleteById(4);

        deletedCourse = courseRepo.findById(4);

        assertFalse(deletedCourse.isPresent(), "Should Return False");

    }

    // Testing CourseService ability to find all courses
    @Test
    public void findAllCourseService() {

        List<Course> courseList = courseService.findAll();

        assertEquals(1, courseList.size());

        Course testCourse = new Course(9, "Intermediate Data Security",
                "Test Data Security Description", "Test Data Security Content");
        courseService.save(testCourse);

        List<Course> updatedCourseList = courseService.findAll();

        assertEquals(2, updatedCourseList.size());
    }

    // Tearing down the database after each test
    @AfterEach
    public void setupAfterTest() {
        jdbcTemplate.execute("DELETE FROM course_info");
    }
}
