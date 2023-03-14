package kr.codesqaud.cafe.domain.repository;

import kr.codesqaud.cafe.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserSaveTest {

    private MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    private SignUpService signUpService = new SignUpServiceImpl(new MemoryMemberRepository());

    @AfterEach
    void afterEach(){
        memberRepository.clear();
    }

    @Test
    @DisplayName("회원 가입 잘 되는지 테스트")
    void signUpTest() {
        // given
        User user = new User("userId", "password", "username", "email.com");

        // when
        signUpService.join(user);

        // then
        User findId = signUpService.findById("userId");
        Assertions.assertThat(user).isEqualTo(findId);
    }
}
