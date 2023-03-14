package kr.codesqaud.cafe.domain.repository;

import kr.codesqaud.cafe.user.User;

public interface MemberRepository {
    void save(User user);

    User findById(String memberId);
}
