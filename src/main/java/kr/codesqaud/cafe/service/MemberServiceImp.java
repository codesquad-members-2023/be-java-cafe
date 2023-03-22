package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.MemberRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MemberServiceImp implements MemberService {

    MemberRepository memberRepository;

    public MemberServiceImp(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member login(String userId, String password) throws NoSuchElementException {
        Member member = memberRepository.findByMemberId(userId).orElseThrow(() -> new NoSuchElementException("해당하는 유저가 없습니다."));

        if (member.getPassword().equals(password)) {
            return member;
        }
        return null;
    }
}
