package kr.codesqaud.cafe.repository.member;

import kr.codesqaud.cafe.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    String save(Member member) ;

    Optional<Member> findById(String userId);

    Optional<Member> findByIdAndPassword(Member member);

    List<Member> findAll();

    void update(Member member);

    void delete(String id);
}
