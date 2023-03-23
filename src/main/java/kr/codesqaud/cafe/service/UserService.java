package kr.codesqaud.cafe.service;


import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> login(String userId, String password) {
        return userRepository.findUserById(userId)
                .filter((user) -> user.getPassword().equals(password));
    }

}
