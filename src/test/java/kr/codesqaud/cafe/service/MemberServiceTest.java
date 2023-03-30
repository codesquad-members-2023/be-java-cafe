package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

        Assertions.assertThat(member).isEqualTo(memberService.findOneMemberByEmail("meena2003@naver.com"));
    }


    @Test
    @DisplayName("가입한 회원의 닉네임을 입력하면 매칭되는 이메일이 나와야 함")
    void findOneMemberByNickname() {
        Member member = new Member("core@naver.com", "core", "112233");
        memberService.join(member);
        String memberEmail = member.getEmail();

        Assertions.assertThat(memberEmail).isEqualTo(memberService.findOneMemberByNickname("core").get().getEmail());
    }

    @Test
    @DisplayName("올바른 패스워드 입력시 회원정보 닉네임이 수정되어야 함")
    void change_UsernickName_Then_RightPassword() {
        Member member = new Member("core@naver.com", "core", "1234");
        memberService.join(member);
        memberService.editeMember(member);
    }
}