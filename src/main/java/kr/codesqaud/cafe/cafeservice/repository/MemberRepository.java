package kr.codesqaud.cafe.cafeservice.repository;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public interface MemberRepository {

    void save(Member member);

    List<Member> findAll();

    Optional<Member> findById(Long userId);

    void update(Long id, Member updateMember);
}
