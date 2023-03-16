package kr.codesqaud.cafe.domain;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.user.User;


public interface UserRepository {
    Optional<User> findById(String id);
    void addUser(User user);
    void updateUser(String userId, String password, String newPassword, String name, String email);

    List<User> getAllUsers();
}
