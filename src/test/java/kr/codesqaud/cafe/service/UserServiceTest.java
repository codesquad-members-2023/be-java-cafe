package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.beans.Transient;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {

    private UserRepository repository;
    private UserService service;
    private User user;

    @BeforeEach
    void init() {
        repository = new MemoryUserRepository();
        service = new UserServiceImpl(repository);
    }

    @AfterEach
    void end() {
        repository.clear();
    }

    @Test
    @DisplayName("회원을 가입하고, 아이디로 회원을 찾을 수 있어야 한다.")
    void join() throws IllegalAccessException {
        User user = User.builder()
                .userId(1L)
                .nickname("user")
                .email("asdf@aasdf")
                .password("1234")
                .build();
        service.join(user);
        assertThat(repository.findById(1L).get().getPassword()).isEqualTo("1234");
    }


    @Test
    @DisplayName("회원을 2개 가입하면, 모두 찾아오기를 했을 때 반환값 크기가 2여야 한다.")
    void findAll() throws IllegalAccessException {
        User user = User.builder()
                .userId(1L)
                .nickname("user")
                .email("asdf@aasdf")
                .password("1234")
                .build();
        service.join(user);
        User user2 = User.builder()
                        .userId(2L)
                        .nickname("user2")
                        .password("1234")
                        .email("asdfasdf@asdf")
                                .build();
        service.join(user2);
        assertThat(repository.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("회원 1명 조회시, 정확한 멤버를 찾아야 한다.")
    void findAUser() throws IllegalAccessException {
        User user = User.builder()
                .userId(1L)
                .nickname("user")
                .email("asdf@aasdf")
                .password("1234")
                .build();
        service.join(user);
        assertThat(service.findUser(1L).getEmail()).isEqualTo("asdf@aasdf");
    }

    @Test
    @DisplayName("중복된 이름이 입력되면 저장되지 않아야 한다.")
    void sameName() throws IllegalAccessException {
        User user = User.builder()
                .userId(1L)
                .nickname("user")
                .email("asdf@aasdf")
                .password("1234")
                .build();
        service.join(user);
        User user2 = User.builder()
                .userId(2L)
                .nickname("user")
                .password("1234")
                .email("asdfasdf@asdf")
                .build();
        service.join(user2);
        assertThatThrownBy(() -> service.join(user2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 이름이 있습니다.");
    }
}
