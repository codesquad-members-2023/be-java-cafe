package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.basic.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class UserMemoryRepository {
    private final Map<String, User> userRepository;

    @Autowired
    public UserMemoryRepository() {
        this.userRepository = new HashMap<>();
    }

    public void join(User user) {
        String userId = user.getUserId();
        userRepository.put(userId, user);
    }

    public User findUser(String userId) {
        return userRepository.get(userId);
    }

    public List<User> findAll() {
        return new ArrayList<>(userRepository.values());
    }

    public void clear() {
        userRepository.clear();
    }

}
