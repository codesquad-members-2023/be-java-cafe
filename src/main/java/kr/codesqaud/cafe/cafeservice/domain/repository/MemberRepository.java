package kr.codesqaud.cafe.cafeservice.domain.repository;

import kr.codesqaud.cafe.cafeservice.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemberRepository {
    final List<Member> store = new ArrayList<>();

    public void save(Member member) {
        store.add(member);
    }

    public List<Member> findAll() {
        return new ArrayList<>(Collections.unmodifiableList(store));
    }

    public Member findOne(Long id) {
        return findById(id).get();
    }

    public Optional<Member> findById(Long userId) {
        return store.stream().filter(member -> member.getId() == userId).findFirst();
    }

    public List<Member> findByName(String userName) {
        return store.stream().filter(member -> member.getUserName() == userName).collect(Collectors.toList());
    }
}
