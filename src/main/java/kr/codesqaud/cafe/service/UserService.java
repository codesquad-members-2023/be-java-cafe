package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;

import java.util.List;

public interface UserService {
    void join(User user);
    User findUser(Long id);
    List<User> findAll();
}
