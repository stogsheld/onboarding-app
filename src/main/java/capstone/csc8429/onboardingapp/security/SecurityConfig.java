package capstone.csc8429.onboardingapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

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

        return new JdbcUserDetailsManager(dataSource);
    }


    // Adding support for user authorisation based on their role
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Authorising http requests based on a user's role
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

        return http.build();
    }

}
