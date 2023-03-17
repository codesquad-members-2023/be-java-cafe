package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void join(Member member) {
        validateDuplicateMember(member);
        memberRepository.saveMember(member);
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findOneMemberbyNickName(member.getNickName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOneMember(String userEmail) {
        return memberRepository.findOneMemberbyEmail(userEmail);
    }

    public Optional<Member> findOneMemberByNickname(String nickName) {
        return memberRepository.findOneMemberbyNickName(nickName);
    }

    public int findTotalNumberOfList() {
        return memberRepository.getSize();
    }
}
