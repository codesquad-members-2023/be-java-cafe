package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService = new MemberService(new MemoryMemberRepository());
    MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();

    @AfterEach
    void clearRepository() {
        memoryMemberRepository.clearRepository();
    }

    @Test
    @DisplayName("회원 가입이 성공적으로 됐는지 확인")
    void join() {
        Member member = new Member("meena2003@naver.com", "core", "12345");
        memberService.join(member);

        Assertions.assertThat(member).isEqualTo(memberService.findOneMember("meena2003@naver.com").get());
    }

    @Test
    @DisplayName("중복한 닉네임으로 가입하려고 하면 예외가 발생해야 함")
    void assertDuplicateNickName() {
        Member member = new Member("meena2003@naver.com", "kim", "2312");
        memberService.join(member);
        Member member2 = new Member("core@naver.com", "kim", "1122");

        assertThrows(IllegalStateException.class, () -> {
                    memberService.join(member2);
                });
    }

    @Test
    @DisplayName("가입한 회원의 닉네임을 입력하면 매칭되는 이메일이 나와야 함")
    void findOneMemberByNickname() {
        Member member = new Member("core@naver.com", "core", "112233");
        memberService.join(member);
        String memberEmail = member.getEmail();

        Assertions.assertThat(memberEmail).isEqualTo(memberService.findOneMemberByNickname("core").get().getEmail());
    }
}