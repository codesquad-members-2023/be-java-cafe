package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryUserRepository implements UserRepository{

    private final List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        if (users.stream().anyMatch(u -> u.getUserId().equals(user.getUserId()))) {
            throw new IllegalArgumentException("[ERROR] ID 중복입니다.");
        }
        user.setId(users.size() + 1);
        users.add(user);
    }

    @Override
    public User findByUserId(int id) {
        if (!users.stream().anyMatch(u -> u.getId() == id)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 회원입니다.");
        }

        return users.get(id - 1);
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public void update(int id, User updateUser, String newPassword) {
        User existUser = findByUserId(id);

        if (!existUser.getPassword().equals(updateUser.getPassword())) {
            throw new IllegalArgumentException("[ERROR] 비밀번호가 틀립니다.");
        }

        existUser.setName(updateUser.getName());
        existUser.setPassword(newPassword);
        existUser.setEmail(updateUser.getEmail());
    }

}
