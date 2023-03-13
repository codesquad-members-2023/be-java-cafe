package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryUserRepository implements UserRepository{
    private final Map<Long, User> store = new HashMap<>();


    @Override
    public void save(User user) {
//        store.put(user.getId(), user);
    }

    @Override
    public User findById(Long userId) {
        return store.get(userId);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) store.values();
    }
}
