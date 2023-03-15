package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService = new MemberService(new MemoryMemberRepository());

    @Test
    @DisplayName("회원 가입이 성공적으로 됐는지 확인")
    void join() {
        Member member = new Member("meena2003@naver.com", "core", "12345");
        memberService.join(member);

        Assertions.assertThat(member).isEqualTo(memberService.findOneMember("meena2003@naver.com").get());
    }
}