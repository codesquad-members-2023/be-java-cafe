package kr.codesqaud.cafe.cafeservice.service;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.exhandler.exception.MemberNotFoundException;
import kr.codesqaud.cafe.cafeservice.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException("member를 찾을수 없습니다"));
    }

    private boolean vaildMemberId(String userName) {
        try {
            memberRepository.findUserName(userName);
        } catch (EmptyResultDataAccessException e) {
            throw new MemberNotFoundException("중복 회원입니다.");
        }
        return true;
    }
}
