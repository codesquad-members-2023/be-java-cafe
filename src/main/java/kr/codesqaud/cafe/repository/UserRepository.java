package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.exceptions.UserInfoException;
import kr.codesqaud.cafe.model.User;


public interface UserRepository {
    User findById(String id) throws UserInfoException;
    void addUser(User user);
    void updateUser(String userId, String password, String newPassword, String name, String email)
        throws UserInfoException;

    List<User> getAllUsers();
}
