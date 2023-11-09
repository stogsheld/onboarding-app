package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.dao.RoleRepo;
import capstone.csc8429.onboardingapp.entity.Completion;
import capstone.csc8429.onboardingapp.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepo roleRepo;

    @Autowired
    public RoleServiceImpl(RoleRepo theRoleRepo) {
        roleRepo = theRoleRepo;
    }

    @Override
    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    @Override
    public List<Role> findAllByUserId(int theId) {
        List<Role> result = roleRepo.findAllByUserId(theId);

        Completion theCompletion = null;

        if (result.isEmpty()) {
            throw new RuntimeException("Did not find user for id - " + theId);
        }

        return result;
    }

    @Override
    public void save(Role theRole) {
        roleRepo.save(theRole);
    }

    @Override
    public void deleteById(int theId) {
        roleRepo.deleteById(theId);
    }
}
