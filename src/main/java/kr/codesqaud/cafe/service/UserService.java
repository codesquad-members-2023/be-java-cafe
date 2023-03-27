package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.JdbcTemplateUserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final JdbcTemplateUserRepository userRepository;

    public UserService(JdbcTemplateUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원 가입 & 업데이트
     */
    public boolean join(User user) {
        return userRepository.save(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    /**
     * 회원 조회
     */
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    /**
     * 회원 검증
     */
    public User passwordCheck(String userId, String password) {
        return userRepository.findByUserId(userId)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(null);
    }
}
