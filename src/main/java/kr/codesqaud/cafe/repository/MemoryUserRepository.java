package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final List<User> store = new ArrayList<>();

    @Override
    public User save(User user) {
        user.setUserNum(store.size() + 1);
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        return store.stream()
                .filter(user -> user.isIdEquals(userId))
                .findFirst();
    }

//    @Override
//    public Optional<User> findByName(String name) {
//        return store.values().stream()
//                .filter(user -> user.getName().equals(name))
//                .findAny();
//    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(Collections.unmodifiableList(store));
    }

    public void clearStore() {
        store.clear();
    }
}
