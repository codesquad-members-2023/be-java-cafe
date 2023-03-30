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
        memberRepository.saveMember(member);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOneMemberByEmail(String userEmail) {
        return memberRepository.findOneMemberbyEmail(userEmail);
    }

    public Optional<Member> findOneMemberByNickname(String nickName) {
        return memberRepository.findOneMemberbyNickName(nickName);
    }

    public int findTotalNumberOfList() {
        return memberRepository.getSize();
    }

    public void editeMember(Member member) {
        String password = memberRepository.findOneMemberbyEmail(member.getEmail()).orElseThrow().getPassword();
        if (!member.getPassword().equals(password)) {
            return;
        }
        memberRepository.editeMember(member);
    }

    public boolean checkMember(Member member, Member storgedMember) {
        if (storgedMember != null && storgedMember.getPassword().equals(member.getPassword())) {
            return true;
        }
        return false;
    }
}
