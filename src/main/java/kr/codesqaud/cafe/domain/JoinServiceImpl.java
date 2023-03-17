package kr.codesqaud.cafe.domain;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.user.User;


public class JoinServiceImpl implements JoinService{
    private final UserRepository userRepository;

    public JoinServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void join(User user) {
        //회원 저장
        userRepository.addUser(user);
    }

    @Override
    public Optional<User> lookupUser(String userId) {
        //회원 이름으로 조회
        return userRepository.findById(userId);
    }

    @Override
    public List<User> lookupAllUser() {
        return userRepository.getAllUsers();
    }

    @Override
    public void updateUser(String userId, String password, String newPassword, String name, String email) {
        //ID로 User 조회
        userRepository.updateUser(userId, password, newPassword, name, email);
    }
}
