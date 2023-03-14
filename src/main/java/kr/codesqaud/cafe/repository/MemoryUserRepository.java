package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemoryUserRepository implements UserRepository{
    private final List<User> store = new ArrayList<>();


    @Override
    public void save(User user) {
        store.add(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return store.stream()
                .filter(user -> Objects.equals(user.getUserId(), userId))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(Collections.unmodifiableList(store));
    }
}
