package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryMemberRepository implements MemberRepository {
    private Map<String, Member> repository;

    public MemoryMemberRepository() {
        repository = new HashMap<>();
    }

    @Override
    public Member saveMember(Member member) {
        repository.put(member.getNickName(), member);
        return member;
    }

    @Override
    public Optional<Member> findOneMemberbyEmail(String email) {
        return repository.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return repository.values().stream().collect(Collectors.toList());
    }

    @Override
    public int getSize() {
        return repository.size();
    }

    public void clearRepository() {
        repository.clear();
    }
}
