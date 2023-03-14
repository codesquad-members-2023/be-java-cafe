package kr.codesqaud.cafe.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.user.User;
import lombok.ToString;


public class MemoryUserRepository implements UserRepository {
    private ArrayList<User> userList = new ArrayList<>();

    @Override
    public Optional<User> findById(String id) {
        //id가 일치하는 User 객체를 반환
        return userList.stream().filter(user -> user.getId().equals(id)).findAny();
    }

    @Override
    public void addUser(User user) {
        userList.add(user);
    }
}
