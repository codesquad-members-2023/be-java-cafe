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

    public Optional<User> login(User user) {
        return userRepository.findUserById(user.getUserId())
                .filter((u) -> u.getPassword().equals(user.getPassword()));
    }

    public Boolean update(String userId, User user) {
        return userRepository.update(userId, user) >= 1;
    }

}
