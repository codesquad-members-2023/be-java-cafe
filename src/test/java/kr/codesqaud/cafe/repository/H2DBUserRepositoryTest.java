package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import static org.junit.jupiter.api.Assertions.*;

class H2DBUserRepositoryTest {

    private H2DBUserRepository repository;

    @BeforeEach
    void init() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:h2:tcp://localhost/~/projects/be-java-cafe/step-3-db", "sa", "");

        repository = new H2DBUserRepository(dataSource);
    }

    @Test
    @DisplayName("h2 DB에 User가 제대로 저장되는지 확인하기 위한 테스트")
    void save() {
        User user = new User("hyun", "1234", "황현", "ghkdgus29@naver.com");

        repository.save(user);
    }
}
