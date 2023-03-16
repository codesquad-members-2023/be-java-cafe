package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private static Map<String, User> store = new HashMap<>();

    public void save(User user) {
        store.put(user.getId(), user);
    }

    public List<User> findAllUsers() {
        return new ArrayList<>(store.values());
    }

    public User findByUserId(String userId) {
        User user = store.get(userId);
        return user;
    }

}
