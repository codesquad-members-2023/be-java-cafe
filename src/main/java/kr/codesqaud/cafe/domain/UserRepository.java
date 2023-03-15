package kr.codesqaud.cafe.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.user.User;


public interface UserRepository {
    Optional<User> findById(String id);
    void addUser(User user);

    List<User> getAllUsers();
}
