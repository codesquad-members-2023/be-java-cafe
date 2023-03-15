package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {
    private Map<String, Member> repository;

    public MemoryMemberRepository() {
        repository = new HashMap<>();
    }

    @Override
    public Member saveMember(Member member) {
        repository.put(member.getUserNickName(), member);
        return member;
    }

    @Override
    public Optional<Member> findOneMemberbyEmail(String userEmail) {
        return repository.values().stream()
                .filter(member -> member.getEmail().equals(userEmail))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(repository.values());
    }

    public void clearRepository() {
        repository.clear();
    }
}
