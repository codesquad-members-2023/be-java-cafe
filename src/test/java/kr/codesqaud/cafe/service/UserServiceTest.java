package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    private UserRepository repository;
    private UserService service;
    private User user;

    @BeforeEach
    void init() {
        repository = new MemoryUserRepository();
        service = new UserServiceImpl(repository);
        user = new User(1L, "testUser", "1234", "name", "email@mail.com", LocalDate.now());
    }

    @Test
    @DisplayName("회원을 가입하고, 아이디로 회원을 찾을 수 있어야 한다.")
    void join() {
        service.join(user);
        assertThat(repository.findById(1L).get()).isEqualTo("testUser");
    }


    @Test
    @DisplayName("회원을 2개 가입하면, 모두 찾아오기를 했을 때 반환값 크기가 2여야 한다.")
    void findAll() {
        service.join(user);
        User user2 = new User(2L, "user2", "1234", "name", "email@email.net", LocalDate.now());
        service.join(user2);

        assertThat(repository.findAll()).hasSize(2);
    }
}
