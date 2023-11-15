package capstone.csc8429.onboardingapp.dao;


import capstone.csc8429.onboardingapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


// Course repository - so app is able to access roles table
public interface RoleRepo extends JpaRepository<Role, Integer> {

    // Find all user's roles by user ID
    public List<Role> findAllByUserId(int userId);
}
