package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member saveMember(Member member);
    Member findOneMemberbyEmail(String email);
    Member findOneMemberbyNickName(String nickName);
    List<Member> findAll();
    int getSize();
    void editeMember(Member member, String email);
}
