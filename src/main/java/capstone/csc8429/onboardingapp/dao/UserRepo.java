package capstone.csc8429.onboardingapp.dao;

import capstone.csc8429.onboardingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


// Course repository - so app is able to access users table
public interface UserRepo extends JpaRepository<User, Integer> {

    // Find all users by team ID
    public List<User> findByTeamId(String teamId);
}
