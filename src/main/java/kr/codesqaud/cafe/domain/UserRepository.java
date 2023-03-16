package kr.codesqaud.cafe.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private static final List<User> userRepository = new ArrayList<>();

    public User save(User user) {
        userRepository.add(user);
        return user;
    }

    public List<User> showAllUsers() {
        List<User> allUsers = new ArrayList<>();
        for (int i = 0; i < userRepository.size(); i++) {
            allUsers.add(userRepository.get(i));
        }
        return allUsers;
    }
}
