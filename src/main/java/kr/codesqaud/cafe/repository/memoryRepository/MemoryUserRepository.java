package kr.codesqaud.cafe.repository.memoryRepository;

import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class MemoryUserRepository implements UserRepository {
    private final List<User> userRepository;

    @Autowired
    public MemoryUserRepository() {
        this.userRepository = new ArrayList();
    }

    public void join(User user) {
        userRepository.add(user);
    }

    public User findUser(String userId) {
        return userRepository.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElseThrow();
    }

    public List<User> findAll() {
        return new ArrayList<>(userRepository);
    }

    @Override
    public boolean update(User user) {
        // TODO : 사용자 찾는 부분과 비밀번호 검증의 부분을 분리하는게 맞을 것 같음
        Optional<User> optionalUser = userRepository.stream()
                .filter((u) -> Objects.equals(u.getUserId(), user.getUserId()))
                .findAny()
                .filter((u) -> Objects.equals(u.getPassword(), user.getPassword()));
        if (optionalUser.isEmpty()) return false;

        User findedUser = optionalUser.get();
        findedUser.setPassword(user.getPassword());
        findedUser.setName(user.getName());
        findedUser.setEmail(user.getEmail());
        return true;
    }

    public void clear() {
        userRepository.clear();
    }

}
