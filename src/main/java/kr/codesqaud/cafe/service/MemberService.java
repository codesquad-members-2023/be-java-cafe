package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Member findOneMemberByEmail(String userEmail) {
        return memberRepository.findOneMemberbyEmail(userEmail);
    }

    public Member findOneMemberByNickname(String nickName) {
        return memberRepository.findOneMemberbyNickName(nickName);
    }

    public int findTotalNumberOfList() {
        return memberRepository.getSize();
    }

    public void editeMember(Member member, String email) {
//        String passward = memberRepository(findOneMemberByEmail(member.getEmail()).getPassword());
//        if (member.getPassword().equals())
        memberRepository.editeMember(member, email);
    }
}
