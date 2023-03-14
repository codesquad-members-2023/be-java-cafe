package kr.codesqaud.cafe.cafeservice.domain.member;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemberRepository {
    final List<Member> store = new ArrayList<>();

    public void save(Member member) {
        store.add(member);
    }

    public Optional<Member> findById(Long userId) {
        return store.stream().filter(member -> member.getId() == userId).findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(Collections.unmodifiableList(store));
    }
}
