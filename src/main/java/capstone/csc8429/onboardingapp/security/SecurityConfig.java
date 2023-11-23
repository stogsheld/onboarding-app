package capstone.csc8429.onboardingapp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.Objects;

/*
SECURITY CONFIGURATION CLASS

This class handles the security for the application, with support for encrypted usernames/passwords
as well as HTTP authorisation. Users will only be able to access content if they are logged in and have
the correct access rights (e.g. employee, admin).
*/

@Configuration
public class SecurityConfig {


    // Adding support for JDBC table (logging in using username & encrypted password)
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Retrieve user from user table in DB by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, pw, active from user where user_id=?");

        // Retrieve roles from roles table in DB by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?");

        return jdbcUserDetailsManager;
    }


    // checking if the app is in 'test' mode
    @Value("${inTestingMode}")
    private boolean inTestingMode;

    // Adding support for user authorisation based on their role
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Authorising http requests based on a user's role
        if (!inTestingMode) {
            http.authorizeHttpRequests(configurer ->
                            configurer
                                    .requestMatchers("/").hasRole("EMPLOYEE")
                                    .requestMatchers("/admin/**").hasRole("ADMIN")
                                    .anyRequest().authenticated()
                    )
                    .formLogin(form ->
                            form
                                    .loginPage("/login")
                                    .loginProcessingUrl("/authenticateTheUser")
                                    .permitAll()
                    )
                    .logout(logout -> logout.permitAll()
                    )
                    .exceptionHandling(configurer ->
                            configurer.accessDeniedPage("/access-denied")
                    );
        }

        return http.build();
    }

}
