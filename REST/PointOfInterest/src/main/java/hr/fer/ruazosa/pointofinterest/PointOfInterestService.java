package hr.fer.ruazosa.pointofinterest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointOfInterestService implements IPointOfInterestService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        if (!checkUsernameUnique(user) || !checkEmailUnique(user))
            return null;

        return userRepository.save(user);
    }

    @Override
    public boolean checkUsernameUnique(User user) {
        List<User> sameUserNameUserList = userRepository.findByUserName(user.getUsername());

        return sameUserNameUserList.size() == 0;
    }

    @Override
    public boolean checkEmailUnique(User user) {
        List<User> sameEmailUserList = userRepository.findByEmail(user.getEmail());

        return sameEmailUserList.size() == 0;
    }

    @Override
    public User loginUser(User user) {
        List<User> loggedUserList = userRepository.findByUserNameAndPassword(user.getUsername(), user.getPassword());
        if (loggedUserList.isEmpty()) {
            return null;
        }
        return userRepository.findByUserNameAndPassword(user.getUsername(), user.getPassword()).get(0);
    }
}
