package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemoryUserRepository implements UserRepository{
    private final List<User> store = new ArrayList<>();

    @Override
    public void save(User user) {
        store.add(user);
    }

    @Override
    public Optional<User> findById(long userId) {
           return store.stream()
                   .filter(user -> user.isIdEquals(userId))
                   .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(Collections.unmodifiableList(store));
    }

    public void clear() {
        store.clear();
    }
}
