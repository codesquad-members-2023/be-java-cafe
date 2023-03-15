package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {
    private static Map<Long, User> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public User save(User user) {
        store.put(++sequence, user);
        return user;
    }

//    @Override
//    public Optional<User> findById(String id) {
//        return Optional.ofNullable(store.get(id));
//    }
//
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
