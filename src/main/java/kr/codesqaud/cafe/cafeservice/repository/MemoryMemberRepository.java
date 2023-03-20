package kr.codesqaud.cafe.cafeservice.repository;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Qualifier
public class MemoryMemberRepository implements MemberRepository {
    final List<Member> store = new ArrayList<>();

    @Override
    public void save(Member member) {
        store.add(member);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(Collections.unmodifiableList(store));
    }


    public Member findOne(Long id) {
        return findById(id).orElseThrow();
    }

    @Override
    public Optional<Member> findById(Long userId) {
        return store.stream().filter(member -> member.getId() == userId).findAny();
    }

    public List<Member> findByName(String userName) {
        return store.stream().filter(member -> member.getUserName() == userName).collect(Collectors.toList());
    }

    @Override
    public void update(Long id, Member updateMember) {
        Member findMember = findById(id).orElseThrow();
        findMember.setEmail(updateMember.getEmail());
        findMember.setPassword(updateMember.getPassword());
        findMember.setUserName(updateMember.getUserName());
    }
}
