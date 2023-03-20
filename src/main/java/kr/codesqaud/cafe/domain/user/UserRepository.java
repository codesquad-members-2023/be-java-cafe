package kr.codesqaud.cafe.domain.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private static final List<User> userRepository = new ArrayList<>();
    private static long sequence = 0L;

    public User save(User user) {
        user.setUserSequence(++sequence);
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

    public User findByUserId(String userId) {
        return userRepository.stream().filter(user -> user.getUserId().equals(userId)).findFirst().orElseThrow();
    }

    public void updateUser(String userId, User updateParam) {
        User user = findByUserId(userId);
        String originalPassword = user.getPassword();
        if (!originalPassword.equals(updateParam.getPassword())) {
            return;
        }
        user.setPassword(updateParam.getPassword());
        user.setName(updateParam.getName());
        user.setEmail(updateParam.getEmail());
    }
}
