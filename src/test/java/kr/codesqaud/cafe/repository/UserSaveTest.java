package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.repository.member.H2DBMemberRepository;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserSaveTest {

    @Autowired
    private MemberRepository memberRepository = new H2DBMemberRepository();

    @AfterEach
    void afterEach() {
        memberRepository.delete("userId");
    }

    @Test
    @DisplayName("회원 가입 잘 되는지 테스트")
    void signUpTest() {
        // given
        User user = new User("userId", "password", "username", "user@email.com");

        // when
        memberRepository.save(user);

        // then
        User findId = memberRepository.findById("userId");
        Assertions.assertThat(user.getUserId()).isEqualTo(findId.getUserId());
    }

    @Test
    @DisplayName("모든 회원 조회 잘 되는지 테스트 - 기존 회원 2명")
    void findAllTest() {
        List<User> all = memberRepository.findAll();

        Assertions.assertThat(all.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원 정보 수정 잘 되는지 테스트")
    void updateUser() {
        User user = new User("userId", "password", "username", "user@email.com");
        memberRepository.save(user);

        User after = new User("userId", "password", "update", "user@email.com");

        memberRepository.updateUser(user.getUserId(), after);
        User updatedUser = memberRepository.findById(user.getUserId());

        Assertions.assertThat(updatedUser.getName()).isEqualTo("update");
    }

    @Test
    @DisplayName("없는 유저 아이디로 찾을 시 에러 발생 ")
    void noUserError() {
        Assertions.assertThatThrownBy(() -> memberRepository.findById("100"));
    }
}
