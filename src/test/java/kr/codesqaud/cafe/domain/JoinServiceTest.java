package kr.codesqaud.cafe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.user.User;

class JoinServiceTest {
    private JoinService joinService = new JoinServiceImpl(new MemoryUserRepository());

    @Transactional
    @Test
    @DisplayName("새로운 User 객체를 인자로 받아 회원 Repository에 등록합니다.")
    void join() {
        //when
        joinService.join(new User("honux","asd","jeong","ho@naver.com"));
        //then
        assertThat(joinService.lookupUser("honux").get().getName()).isEqualTo("jeong");
    }

    @Transactional
    @Test
    @DisplayName("UserId가 일치하는 회원을 Repository에서 조회합니다.")
    void lookupUser() {
        //given
        joinService.join(new User("conux","asd","J","ho@naver.com"));
        //then
        assertThat(joinService.lookupUser("conux").get().getName()).isEqualTo("J");
    }
}
