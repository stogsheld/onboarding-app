package capstone.csc8429.onboardingapp.dao;

import capstone.csc8429.onboardingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {

    public List<User> findByTeamId(String teamId);
}
