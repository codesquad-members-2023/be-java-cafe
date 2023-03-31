package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.MySQLUserRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class LoginServiceTest {

    @Autowired
    private DataSource dataSource;
    private UserRepository repository;
    private LoginService loginService;

    @BeforeEach
    void init() {
        repository = new MySQLUserRepository(dataSource);
        loginService = new LoginService(repository);
    }

    @AfterEach
    void clear() {
        JdbcTemplate template = new JdbcTemplate(dataSource);

        String sql = "delete from users;" +
                "alter table users alter column id restart with 1";

        template.update(sql);
    }

    @Test
    @DisplayName("loginId와 userId 가 같은 회원을 조회")
    void login() {
        User user1 = new User("Hyun", "1234", "황현", "ghkdgus29@naver.com");
        User user2 = new User("Yoon", "12345", "황윤", "ghkddbs28@naver.com");

        repository.save(user1);
        repository.save(user2);

        String userId = "Hyun";
        String password = "1234";

        User loginUser = loginService.login(userId, password);

        assertThat(loginUser.getName()).isEqualTo(user1.getName());
        assertThat(loginUser.getEmail()).isEqualTo(user1.getEmail());
    }

    @Test
    @DisplayName("로그인 시 회원의 패스워드가 다르면 예외를 발생")
    void wrongPassword() {
        User user1 = new User("Hyun", "1234", "황현", "ghkdgus29@naver.com");
        User user2 = new User("Yoon", "12345", "황윤", "ghkddbs28@naver.com");

        repository.save(user1);
        repository.save(user2);

        String userId = "Hyun";
        String password = "1111";

        assertThatThrownBy(() -> {
            loginService.login(userId, password);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 비밀번호가 틀립니다.");
    }
}
