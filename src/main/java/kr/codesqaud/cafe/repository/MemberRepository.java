package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member saveMember(Member member);
    Optional<Member> findOneMemberbyEmail(String userName);
    List<Member> findAll();
    int getSize();
}
