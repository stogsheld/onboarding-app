package capstone.csc8429.onboardingapp;

import capstone.csc8429.onboardingapp.rest.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

// Smoke Tests - Making sure that all REST Controllers exist in the app
@SpringBootTest
public class SmokeTests {

    @Autowired
    private AdminRestController adminRestController;
    @Test
    void adminContextLoads() throws Exception {
        assertThat(adminRestController).isNotNull();
    }


    @Autowired
    private CourseRestController courseRestController;
    @Test
    void courseContextLoads() throws Exception {
        assertThat(courseRestController).isNotNull();
    }


    @Autowired
    private HomeRestController homeRestController;
    @Test
    void homeContextLoads() throws Exception {
        assertThat(homeRestController).isNotNull();
    }


    @Autowired
    private LoginRestController loginRestController;
    @Test
    void loginContextLoads() throws Exception {
        assertThat(loginRestController).isNotNull();
    }
}
