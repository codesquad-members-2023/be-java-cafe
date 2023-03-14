package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void join(User user) throws IllegalAccessException;
    User findUser(Long id);
    List<User> findAll();
}
