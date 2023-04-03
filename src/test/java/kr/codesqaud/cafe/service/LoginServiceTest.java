package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.domain.dto.LoginForm;
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

    @BeforeEach
    void init() {
        repository = new MySQLUserRepository(dataSource);
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


        LoginForm loginForm = new LoginForm("Hyun", "1234");

        User loginUser = repository.findByUserId(loginForm.getUserId());

        assertThat(loginUser.getName()).isEqualTo(user1.getName());
        assertThat(loginUser.getEmail()).isEqualTo(user1.getEmail());
    }
}
