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
}
