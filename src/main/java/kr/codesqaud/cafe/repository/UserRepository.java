package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    User findById(int id);

    User findByUserId(String userId);

    List<User> findAll();

    void update(int id, User updateUser, String newPassword);
}
