package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class H2DBUserRepositoryTest {

    private H2DBUserRepository repository;

    @BeforeEach
    void init() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:h2:tcp://localhost/~/projects/be-java-cafe/step-3-db", "sa", "");

        repository = new H2DBUserRepository(dataSource);
    }

    @Test
    @DisplayName("h2 DB에 User가 제대로 저장된다.")
    void save() {
        User user = new User("hyun", "1234", "황현", "ghkdgus29@naver.com");

        repository.save(user);
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

        User updateUser = new User("hyun", "1234", "황현태", "ghkdgus29@gmail.com");

        repository.update(1, updateUser, "0429");
    }

}
