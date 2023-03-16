package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryUserRepository {

    private final List<User> users = new ArrayList<>();

    public void save(User user) {
        if (users.stream().anyMatch(u -> u.getUserId().equals(user.getUserId()))) {
            throw new IllegalArgumentException("[ERROR] ID 중복입니다.");
        }
        user.setId(users.size() + 1);
        users.add(user);
    }

    public User findByUserId(int id) {
        if (!users.stream().anyMatch(u -> u.getId() == id)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 회원입니다.");
        }

        return users.get(id - 1);
    }

    public List<User> findAll() {
        return users;
    }

}
