package kr.codesqaud.cafe.service.impl;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.service.JoinService;
import kr.codesqaud.cafe.model.User;
import kr.codesqaud.cafe.utils.UserInfoException;


public class JoinServiceImpl implements JoinService {

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
    public User lookupUser(String userId) throws UserInfoException {
        //회원 이름으로 조회
        return userRepository.findById(userId);

    }

    @Override
    public List<User> lookupAllUser() {
        return userRepository.getAllUsers();
    }

    @Override
    public void updateUser(String userId, String password, String newPassword, String name,
        String email) throws UserInfoException {
        //ID로 User 조회
        userRepository.updateUser(userId, password, newPassword, name, email);
    }
}
