package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member saveMember(Member member);
    Optional<Member> findOneMemberbyEmail(String userName);
    Optional<Member> findOneMemberbyNickName(String nickName);
    List<Member> findAll();
    int getSize();
}
