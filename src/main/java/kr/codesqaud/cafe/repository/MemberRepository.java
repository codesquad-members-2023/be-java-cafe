package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;

public interface MemberRepository {
    void saveMember(Member member);
    void findOneMember(String userName);
    void findAll();
}
