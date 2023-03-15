package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {
    private static Map<String, User> store = new LinkedHashMap<>();
    private static long sequence = 0L;

    @Override
    public User save(User user) {
        user.setUserNum(++sequence);
        store.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(store.get(userId));
    }

//    @Override
//    public Optional<User> findByName(String name) {
//        return store.values().stream()
//                .filter(user -> user.getName().equals(name))
//                .findAny();
//    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
