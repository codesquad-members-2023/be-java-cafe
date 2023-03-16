package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private static final List<User> users = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public void save(User user) {
        users.add(user);
        logger.info("save");
    }

    public Optional<User> findByName(String name) {
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .findAny();
    }

    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    public List<User> findAll() {
        return users;
    }

    public void clearUsers() {
        users.clear();
    }
}
