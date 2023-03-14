package kr.codesqaud.cafe.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.user.User;
import lombok.ToString;

@Repository
@ToString
public class MemoryUserRepository implements UserRepository {
    private Map<String, User> userMap = new HashMap<>();

    @Override
    public User findById(String id) {
        //null인 경우, Optional로 반환할지 고려
        return userMap.get(id);
    }

    @Override
    public void addUser(User user) {
        userMap.put(user.getId(), user);
    }
}
