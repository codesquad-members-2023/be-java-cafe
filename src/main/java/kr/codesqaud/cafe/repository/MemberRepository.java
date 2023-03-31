package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    void saveMember(Member member);

    Optional<Member> findOneMemberbyEmail(String email);

    Optional<Member> findOneMemberbyNickName(String nickName);

    List<Member> findAll();

    int getSize();

    void editeMember(Member member);
}
