package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.User;

import java.util.List;

public interface MemberRepository {
    void save(User user);

    User findById(String memberId);
    List<User> findAll();
}
