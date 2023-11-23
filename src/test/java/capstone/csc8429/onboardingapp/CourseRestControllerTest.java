package capstone.csc8429.onboardingapp;

import capstone.csc8429.onboardingapp.entity.Course;
import capstone.csc8429.onboardingapp.service.CourseServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/test.properties")
@AutoConfigureMockMvc
@SpringBootTest(classes = OnboardingAppApplication.class)
public class CourseRestControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CourseServiceImpl courseServiceMock;

    // Setting up a fresh database with the below entry for every test
    @BeforeEach
    public void setupDatabase() {
        jdbcTemplate.execute("insert into course_info(course_id, course_name, course_description, course_content)" +
                "values (4, 'Intro to Security', 'Description Test', 'Content Test')");
    }


    // Mock MVC - Testing that the 'list-courses' html page is returned
    // when http://localhost:8080/courses/list is accessed
    @Test
    public void getCoursesHttpRequest() throws Exception {

        Course courseOne = new Course(6, "Course Example One",
                "Course Description", "Course Content");

        when(courseServiceMock.findById(6)).thenReturn(courseOne);

        assertEquals(courseOne, courseServiceMock.findById(6));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/courses/list"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "courses/list-courses");
    }

    // Tearing down the database after each test
    @AfterEach
    public void setupAfterTest() {
        jdbcTemplate.execute("DELETE FROM course_info");
    }
}
