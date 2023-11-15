package capstone.csc8429.onboardingapp.service;

import capstone.csc8429.onboardingapp.dao.UserRepo;
import capstone.csc8429.onboardingapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


// User Service Implementation so that the app can implement JPARepository DB methods in UserRepo
@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo theUserRepo) {
        userRepo = theUserRepo;
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findById(int theId) {
        Optional<User> result = userRepo.findById(theId);

        User theUser  = null;

        if (result.isPresent()) {
            theUser = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find user for id - " + theId);
        }

        return theUser;
    }

    @Override
    public List<User> findByTeamId(String teamId) {
        return userRepo.findByTeamId(teamId);
    }

    @Override
    public void save(User theUser) {
        userRepo.save(theUser);
    }

    @Override
    public void deleteById(int theId) {
        userRepo.deleteById(theId);
    }
}
