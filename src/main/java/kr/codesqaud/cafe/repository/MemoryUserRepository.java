package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Stream;

@Repository
public class MemoryUserRepository implements UserRepository{

    private static List<User> store = new ArrayList<>();

    @Override
    public User save(User user) {
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        return store.stream().filter(user -> user.getUserId().equals(userId)).findFirst();    }

    @Override
    public Optional<User> findByName(String userName) {
        return store.stream().filter(user -> user.getUserName().equals(userName)).findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store);
    }
    
    public void clearStore(){
        store.clear();
    }
}
