package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class H2DBUserRepositoryTest {

    private DataSource dataSource;
    private H2DBUserRepository repository;

    @BeforeEach
    void init() {
        dataSource = new DriverManagerDataSource("jdbc:h2:tcp://localhost/~/projects/be-java-cafe/step-3-db", "sa", "");
        repository = new H2DBUserRepository(dataSource);
    }

    @AfterEach
    void clear() {
        JdbcTemplate template = new JdbcTemplate(dataSource);

        String sql = "delete from users;" +
                "alter table users alter column id restart with 1";

        template.update(sql);
    }

    @Test
    @DisplayName("h2 DB에서 User를 id로 조회 가능하다.")
    void findById() {
        User user = new User("hyun", "1234", "황현", "ghkdgus29@naver.com");

        repository.save(user);
        User findUser = repository.findById(1);

        assertThat(findUser.getUserId()).isEqualTo(user.getUserId());
        assertThat(findUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(findUser.getName()).isEqualTo(user.getName());
        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("메모리 저장소에 존재하지 않는 회원 ID 검색시 예외가 터지는지 확인")
    void validateFindByUserId() {
        User user1 = new User("Hyun", "1234", "황현", "ghkdgus29@naver.com");
        User user2 = new User("Yoon", "12345", "황윤", "ghkddbs28@naver.com");

        repository.save(user1);
        repository.save(user2);

        assertThatThrownBy(() -> {
            repository.findById(3);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 회원입니다.");
    }

    @Test
    @DisplayName("h2 DB 에서 모든 User를 조회할 수 있다.")
    void findAll() {
        User user1 = new User("hyun", "1234", "황현", "ghkdgus29@naver.com");
        User user2 = new User("yoon", "4321", "황윤", "ghkddbs28@naver.com");

        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findAll();

        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("h2 DB 에서 특정 User를 수정할 수 있다.")
    void update() {
        User user1 = new User("hyun", "1234", "황현", "ghkdgus29@naver.com");
        User user2 = new User("yoon", "4321", "황윤", "ghkddbs28@naver.com");

        repository.save(user1);
        repository.save(user2);

        User updateUser = new User("hyun", "0429", "황현태", "ghkdgus29@gmail.com");

        repository.update(1, updateUser, "1234");
    }

    @Test
    @DisplayName("회원의 비밀번호가 일치하지 않는 경우 수정이 불가능")
    void validatePasswordCheck() {
        User user = new User("Hyun", "1234", "황현", "ghkdgus29@naver.com");
        User updateUser = new User("Yoon", "4321", "황윤", "ghkddbs28@naver.com");

        repository.save(user);
        int userId = repository.findById(1).getId();

        assertThatThrownBy(() -> {
            repository.update(userId, updateUser, "3333");
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 비밀번호가 틀립니다.");
    }

}
