package capstone.csc8429.onboardingapp.service;


import capstone.csc8429.onboardingapp.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    List<Role> findAllByUserId(int theId);

    void save(Role theRole);

    void deleteById(int theId);
}
