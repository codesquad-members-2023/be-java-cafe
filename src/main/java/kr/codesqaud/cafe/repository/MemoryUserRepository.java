package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository{
    private final List<User> store = new ArrayList<>();

    @Override
    public void save(User user) {
        if (vaildName(user.getNickname())) {
            store.add(user);
        }
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

    @Override
    public void clear() {
        store.clear();
    }

    @Override
    public boolean vaildName(String userName) {
        return store.stream().noneMatch(e -> e.isNameEquals(userName));
    }
}
