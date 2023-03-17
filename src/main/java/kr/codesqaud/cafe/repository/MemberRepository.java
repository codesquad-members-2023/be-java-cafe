package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.User;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    void save(User user);

    Optional<User> findById(String userId);

    List<User> findAll();

    void updateUser(String userId, User updateUser);
}
