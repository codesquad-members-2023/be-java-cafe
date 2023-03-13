package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryUserRepository {

    private final Map<String, User> users = new HashMap<>();

    public void save(User user) {
        if (users.containsKey(user.getUserId())) {
            throw new IllegalArgumentException("[ERROR] ID 중복입니다.");
        }

        users.put(user.getUserId(), user);
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

}
