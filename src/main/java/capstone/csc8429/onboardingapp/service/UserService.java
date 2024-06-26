package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.entity.User;
import java.util.List;


// User Service so that the app can implement JPARepository DB methods in UserRepo
public interface UserService {

    List<User> findAll();

    User findById(int theId);

    List <User> findByTeamId(String teamId);

    void save(User theUser);

    void deleteById(int theId);
}
