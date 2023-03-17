package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.basic.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class MemoryUserRepository {
    private final List<User> userRepository;

    @Autowired
    public MemoryUserRepository() {
        this.userRepository = new ArrayList();
    }

    public void join(User user) {
        userRepository.add(user);
    }

    public User findUser(String userId) {
        return userRepository.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElseThrow();
    }

    public List<User> findAll() {
        return new ArrayList<>(userRepository);
    }

    public void clear() {
        userRepository.clear();
    }

}
