package kr.codesqaud.cafe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.codesqaud.cafe.user.User;

class JoinServiceTest {
    private JoinService joinService = new JoinServiceImpl(new MemoryUserRepository());

    @Test
    @DisplayName("새로운 User 객체를 인자로 받아 회원 Repository에 등록합니다.")
    void join() {
        //when
        joinService.join(new User("honux", "asd", "jeong", "ho@naver.com"));
        //then
        assertThat(joinService.lookupUser("honux").get().getName()).isEqualTo("jeong");
    }

    @Test
    @DisplayName("UserId가 일치하는 회원을 Repository에서 조회합니다.")
    void lookupUser() {
        //given
        joinService.join(new User("conux", "asd", "J", "ho@naver.com"));
        //then
        assertThat(joinService.lookupUser("conux").get().getName()).isEqualTo("J");
    }

    @Test
    @DisplayName("모든 회원을 Repository에서 조회한다.")
    void lookupAllUsers() {
        //given
        joinService.join(new User("conux", "asd", "J", "ho@naver.com"));
        joinService.join(new User("tonux", "asd", "Js", "ho2@naver.com"));
        List<User> expected = List.of(new User("conux", "asd", "J", "ho@naver.com"),
                new User("tonux", "asd", "Js", "ho2@naver.com"));

        //then
        assertThat(isEqual(joinService.lookupAllUser(), expected)).isTrue();
    }

    private boolean isEqual(List<User> userList, List<User> expected) {
        if (userList==null) {
            return expected==null;
        }
        return userList.equals(expected);
    }


}
