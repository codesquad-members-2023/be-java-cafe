package kr.codesqaud.cafe.domain.repository;

import kr.codesqaud.cafe.repository.MemoryMemberRepository;
import kr.codesqaud.cafe.repository.SignUpService;
import kr.codesqaud.cafe.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class UserSaveTest {

    private SignUpService signUpService = new SignUpService(new MemoryMemberRepository());

    @Test
    @DisplayName("회원 가입 잘 되는지 테스트")
    void signUpTest() {
        // given
        User user = new User("userId", "password", "username", "user@email.com");

        // when
        signUpService.join(user);

        // then
        User findId = signUpService.findById("userId").get();
        Assertions.assertThat(user).isEqualTo(findId);
    }

    @Test
    @DisplayName("모든 회원 조회 잘 되는지 테스트")
    void findAllTest() {
        User user = new User("userId", "password", "username", "user@email.com");
        User user2 = new User("userId", "password", "username", "user@email.com");

        signUpService.join(user);
        signUpService.join(user2);

        List<User> all = signUpService.findAll();

        Assertions.assertThat(all.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원 정보 수정 잘 되는지 테스트")
    void updateUser() {
        User user = new User("userId", "password", "username", "user@email.com");
        signUpService.join(user);

        User updateUser = new User("update", "password", "username", "user@email.com");

        signUpService.updateUser(user.getUserId(), updateUser);

        Assertions.assertThat(user.getUserId()).isEqualTo("update");
    }
}
