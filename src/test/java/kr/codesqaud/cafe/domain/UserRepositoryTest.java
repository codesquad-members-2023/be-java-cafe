package kr.codesqaud.cafe.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.codesqaud.cafe.user.User;

class UserRepositoryTest {
    private UserRepository userRepository = new MemoryUserRepository();

    @Test
    @DisplayName("ID가 일치하는 유저를 찾아서 반환한다.")
    void findById() {
        //given

        //when
        userRepository.addUser(new User("crong", "123", "krong", "crong@naver.com"));

        User user = userRepository.findById("crong").get();
        //then
        assertThat(user.getName()).isEqualTo("krong");
    }

    @Test
    @DisplayName("유저를 추가한다.")
    void addUser() {
        //given

        //when
        userRepository.addUser(new User("poro", "123", "gwonwoo", "ngw7617@naver.com"));
        //then
        assertThat(userRepository.findById("poro").get().getName()).isEqualTo("gwonwoo");
    }

    @Test
    @DisplayName("유저의 정보를 업데이트한다.")
    void updateUser() {
        //given
        User user = new User("conux", "asd", "J", "ho@naver.com");
        userRepository.addUser(user);
        //when
        userRepository.updateUser("conux", "asd", "skarnjsdn1", "Jayho", "ngw7617@naver.com");
        //then
        assertAll(
                () -> assertThat(user.getName()).isEqualTo("Jayho"),
                () -> assertThat(user.getPassword()).isEqualTo("skarnjsdn1"),
                () -> assertThat(user.getEmail()).isEqualTo("ngw7617@naver.com")
        );
    }
}
