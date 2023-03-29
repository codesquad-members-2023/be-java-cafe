package kr.codesqaud.cafe.service;


import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.basic.UserDTO;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> login(UserDTO userDTO) {
        return userRepository.findUserById(userDTO.getUserId())
                .filter((user) -> user.getPassword().equals(userDTO.getPassword()));
    }

    public Boolean update(UserDTO userDTO) {
        return userRepository.update(userDTO) >= 1;
    }

}
