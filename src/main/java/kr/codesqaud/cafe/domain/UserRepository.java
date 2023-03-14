package kr.codesqaud.cafe.domain;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.user.User;


public interface UserRepository {
    User findById(String id);
    void addUser(User user);
}
