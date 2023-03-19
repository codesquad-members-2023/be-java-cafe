package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(Member user);
    Optional<Member> findById(long userId);
    List<Member> findAll();
    void clear();
    boolean vaildName(String userName);

    void update(Member exMember, Member newMember);
}
