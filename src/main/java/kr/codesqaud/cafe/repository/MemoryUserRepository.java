package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.model.User;

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

    @Override
    public void updateUser(String userId, String password, String newPassword, String name, String email) {
        User user = findById(userId).orElseThrow(()->new IllegalArgumentException("[ERROR] Wrong password"));
        //비밀번호 일치 확인
        user.validate(password);

        user.updateUserInfo(name, newPassword, email);
    }
}
