package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String loginId, String loginPassword) {
        User findUser = userRepository.findByUserId(loginId);

        if (!findUser.getPassword().equals(loginPassword)) {
            throw new IllegalArgumentException("[ERROR] 비밀번호가 틀립니다.");
        }

        return findUser;
    }
}
