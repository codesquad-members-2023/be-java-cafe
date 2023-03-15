package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final List<User> userList;

    public UserService(List<User> userList) {
        this.userList = userList;
    }

    public void createUser(User user) {
        userList.add(user);
    }

    public List<User> findUserAll() {
        return userList;
    }

    public List<User> findUserByUserId(String userId) {
        return userList.stream()
                .filter(s -> s.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
