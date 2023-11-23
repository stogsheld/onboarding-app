package capstone.csc8429.onboardingapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("/test.properties")
@SpringBootTest(classes = OnboardingAppApplication.class)
class OnboardingAppApplicationTests {

	@Test
	void contextLoads() {
	}

}
