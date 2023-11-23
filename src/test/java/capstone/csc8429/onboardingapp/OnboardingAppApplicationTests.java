package capstone.csc8429.onboardingapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
class OnboardingAppApplicationTests {

	@Autowired
	ApplicationContext context;

	@Test
	void contextLoads() {
	}

}
