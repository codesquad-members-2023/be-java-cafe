package kr.codesqaud.cafe.domain.repository;

import kr.codesqaud.cafe.repository.MemoryMemberRepository;
import kr.codesqaud.cafe.repository.SignUpService;
import kr.codesqaud.cafe.repository.SignUpServiceImpl;
import kr.codesqaud.cafe.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class UserSaveTest {

    private SignUpService signUpService = new SignUpServiceImpl(new MemoryMemberRepository());

    @Test
    @DisplayName("회원 가입 잘 되는지 테스트")
    void signUpTest() {
        // given
        User user = new User.Builder()
                .userId("userId")
                .password("password")
                .name("username")
                .email("email.com")
                .build();

        // when
        signUpService.join(user);

        // then
        User findId = signUpService.findById("userId").get();
        Assertions.assertThat(user).isEqualTo(findId);
    }

    @Test
    @DisplayName("모든 회원 조회 잘 되는지 테스트")
    void findAllTest() {
        User user = new User.Builder()
                .userId("userId")
                .password("password")
                .name("username")
                .email("email.com")
                .build();

        User user2 = new User.Builder()
                .userId("userId2")
                .password("password")
                .name("username")
                .email("email.com")
                .build();

        signUpService.join(user);
        signUpService.join(user2);

        List<User> all = signUpService.findAll();

        Assertions.assertThat(all.size()).isEqualTo(2);
    }

}
