package kr.codesqaud.cafe.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.user.User;

public class MemoryUserRepository implements UserRepository {
    private ArrayList<User> userList = new ArrayList<>();
    private long currentIndexCounter = 1;

    @Override
    public Optional<User> findById(String id) {
        //id가 일치하는 User 객체를 반환
        return userList.stream().filter(user -> user.getId().equals(id)).findAny();
    }

    @Override
    public List<User> getAllUsers() {
        return userList;
    }

    @Override
    public void addUser(User user) {
        user.setIndex(currentIndexCounter++);
        userList.add(user);
    }
}
