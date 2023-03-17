package kr.codesqaud.cafe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

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
        User user1 = new User("conux", "asd", "J", "ho@naver.com");
        User user2 = new User("tonux", "asd", "Js", "ho2@naver.com");
        //given
        joinService.join(user1);
        joinService.join(user2);
        //then
        assertThat(joinService.lookupAllUser()).contains(user1, user2);
    }

    @Test
    @DisplayName("비밀번호가 일치하면 회원 정보를 수정할 수 있다.")
    void updateUserWithCorrectPassword() {
        //given
        User user = new User("conux", "asd", "J", "ho@naver.com");
        joinService.join(user);
        //when
        joinService.updateUser("conux", "asd", "skarnjsdn1", "Jayho", "ngw7617@naver.com");
        //then
        assertAll(
                () -> assertThat(user.getName()).isEqualTo("Jayho"),
                () -> assertThat(user.getPassword()).isEqualTo("skarnjsdn1"),
                () -> assertThat(user.getEmail()).isEqualTo("ngw7617@naver.com")
        );
    }

    @Test
    @DisplayName("비밀번호가 틀리면 예외를 발생시킨다.")
    void updateUserWithWrongPassword() {
        //given
        User user1 = new User("conux", "asd", "J", "ho@naver.com");
        User user2 = new User("conux", "skarnjsdn1", "Jayho", "ngw7617@naver.com");
        joinService.join(user1);

        //when, then
        assertThatThrownBy(() -> {
            joinService.updateUser("conux", "asd123", "skarnjsdn1", "Jayho", "ngw7617@naver.com");
        }).isInstanceOf(IllegalArgumentException.class);
    }

}
