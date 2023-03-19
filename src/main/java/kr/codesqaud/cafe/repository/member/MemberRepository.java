package kr.codesqaud.cafe.repository.member;

import kr.codesqaud.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    void save(User user);

    User findById(String userId);

    List<User> findAll();

    void updateUser(String userId, User updateUser);

    void delete(String userId);
}
