package kr.codesqaud.cafe.repository.users;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static Map<String, User> store = new HashMap<>();

    public void save(User user) {
        store.put(user.getUserId(), user);
    }

    public List<User> findAllUsers() {
        return new ArrayList<>(store.values());
    }

    @Override
    public User findUserById(long id) {
        return store.get(id);
    }

    @Override
    public Optional<User> findUserWithMatchedPassword(String userId, String password) {
        User user = store.get(userId);

        return Optional.ofNullable(user);
    }

    @Override
    public void update(User user, long id) {
        store.put(user.getUserId(), user);
    }

    public User findUserById(String userId) {
        User user = store.get(userId);
        return user;
    }
}
