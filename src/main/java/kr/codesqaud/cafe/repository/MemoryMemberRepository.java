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
        repository.put(member.getEmail(), member);
        return member;
    }

    @Override
    public Optional<Member> findOneMemberbyEmail(String email) {
        return Optional.ofNullable(repository.get(email));
    }

    @Override
    public Optional<Member> findOneMemberbyNickName(String nickName) {
        return repository.values().stream()
                .filter(member -> member.getNickName().equals(nickName))
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

    @Override
    public void editeMember(Member member) {
        repository.put(member.getEmail(), member);
    }

    public void clearRepository() {
        repository.clear();
    }
}
