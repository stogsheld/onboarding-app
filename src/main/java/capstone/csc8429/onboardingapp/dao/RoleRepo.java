package capstone.csc8429.onboardingapp.dao;


import capstone.csc8429.onboardingapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RoleRepo extends JpaRepository<Role, Integer> {

    public List<Role> findAllByUserId(int userId);
}
