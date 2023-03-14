package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.User;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    void save(User user);

    Optional<User> findById(String memberId);

    List<User> findAll();
}
