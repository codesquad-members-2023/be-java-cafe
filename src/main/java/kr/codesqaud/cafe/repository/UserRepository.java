package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private static List<User> users = new ArrayList<>();

    public User save(User user) {
        users.add(user);
        return user;
    }

    public Optional<User> findById(String userId) {
        return users.stream().filter(user -> user.getUserId().equals(userId)).findFirst();
    }

    public Optional<User> findByName(String userName) {
        return users.stream().filter(user -> user.getUserName().equals(userName)).findAny();
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public void updateUser(User user){
        int userIndex = users.indexOf(user);
        users.set(userIndex,user);
    }



    public void clearStore() {
        users.clear();
    }
}
