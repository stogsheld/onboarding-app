package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.entity.Role;
import java.util.List;


// Role Service so that the app can implement JPARepository DB methods in RoleRepo
public interface RoleService {

    List<Role> findAll();

    List<Role> findAllByUserId(int theId);

    void save(Role theRole);

    void deleteById(int theId);
}
