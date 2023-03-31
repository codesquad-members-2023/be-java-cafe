package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.repository.member.H2JDBCMemberRepository;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@JdbcTest
class UserTest {

    private final MemberRepository memberRepository;

    @Autowired
    public UserTest(DataSource dataSource) {
        this.memberRepository = new H2JDBCMemberRepository(dataSource);
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

        memberRepository.updateUser(after);
        User updatedUser = memberRepository.findById(user.getUserId());

        Assertions.assertThat(updatedUser.getName()).isEqualTo("update");
    }

    @Test
    @DisplayName("없는 유저 아이디로 찾을 시 에러 발생 ")
    void noUserError() {
        Assertions.assertThatThrownBy(() -> memberRepository.findById("100"));
    }
}
