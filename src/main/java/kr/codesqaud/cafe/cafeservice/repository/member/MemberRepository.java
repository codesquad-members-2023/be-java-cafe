package kr.codesqaud.cafe.cafeservice.repository.member;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface MemberRepository {

    void save(Member member);

    List<Member> findAll();

    Optional<Member> findById(Long userId);

    void update(Long id, Member updateMember);

    Optional<Member> findByLoginId(String loginId);
}
