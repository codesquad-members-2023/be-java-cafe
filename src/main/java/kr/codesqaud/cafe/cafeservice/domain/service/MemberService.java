package kr.codesqaud.cafe.cafeservice.domain.service;

import kr.codesqaud.cafe.cafeservice.domain.member.Member;
import kr.codesqaud.cafe.cafeservice.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService() {
        this.memberRepository = new MemberRepository();
    }

    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMember = memberRepository.findByName(member.getUserName());
        if (!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }
}
