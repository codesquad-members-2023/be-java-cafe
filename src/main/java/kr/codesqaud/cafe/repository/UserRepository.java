package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private static List<User> store = new ArrayList<>();
    private static UserRepository instance;

    public static UserRepository getInstance() {
        return instance;
    }

    public User save(User user) {
        store.add(user);
        return user;
    }

    public Optional<User> findById(String userId) {
        return store.stream().filter(user -> user.getUserId().equals(userId)).findFirst();    }

    public Optional<User> findByName(String userName) {
        return store.stream().filter(user -> user.getUserName().equals(userName)).findAny();
    }

    public List<User> findAll() {
        return new ArrayList<>(store);
    }
    
    public void clearStore(){
        store.clear();
    }
}
