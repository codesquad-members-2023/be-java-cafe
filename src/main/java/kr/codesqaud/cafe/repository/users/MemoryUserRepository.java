package kr.codesqaud.cafe.repository.users;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return null;
    }

    @Override
    public User findDBUser(String userId, String password) {
        return new User();
    }

    public User findUserById(String userId) {
        User user = store.get(userId);
        return user;
    }
}
