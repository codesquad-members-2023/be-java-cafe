package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    void save(Member user);
    Member findById(Long userId);
    Optional<Member> findByMemberId(String userId);
    List<Member> findAll();
    boolean validMemberId(String userName);
    void update(Member exMember, Member newMember);
}
