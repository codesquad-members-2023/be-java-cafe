package kr.codesqaud.cafe.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import kr.codesqaud.cafe.model.User;
import kr.codesqaud.cafe.repository.JdbcUserRepository;
import kr.codesqaud.cafe.repository.UserRepository;

@SpringBootTest
class UserRepositoryTest {
    private DataSource dataSource;
    private UserRepository userRepository;
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        dataSource = new DriverManagerDataSource("jdbc:h2:mem:test", "sa", "");
        userRepository = new JdbcUserRepository(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);

        User user1 = new User("conux", "asd", "J", "ho@naver.com");
        User user2 = new User("tonux", "asd", "Js", "ho2@naver.com");
        userRepository.addUser(user1);
        userRepository.addUser(user2);
    }

    @AfterEach
    public void clean() {
        jdbcTemplate.update("delete from users; alter table users alter column idx restart with 1;");
    }

    @Test
    @DisplayName("ID가 일치하는 유저를 찾아서 반환한다.")
    void findById() {
        User user = userRepository.findById("conux");
        //then
        assertThat(user.getName()).isEqualTo("J");
    }

    @Test
    @DisplayName("유저를 추가한다.")
    void addUser() {
        userRepository.addUser(new User("poro", "123", "gwonwoo", "ngw7617@naver.com"));
        //then
        assertThat(userRepository.findById("poro").getName()).isEqualTo("gwonwoo");
    }

    @Test
    @DisplayName("유저의 정보를 업데이트한다.")
    void updateUser() {
        //when
        userRepository.updateUser("conux", "asd", "skarnjsdn1", "Jayho", "ngw7617@naver.com");
        //then
        User foundUser = userRepository.findById("conux");
        assertAll(
                () -> assertThat(foundUser.getName()).isEqualTo("Jayho"),
                () -> assertThat(foundUser.getPassword()).isEqualTo("skarnjsdn1"),
                () -> assertThat(foundUser.getEmail()).isEqualTo("ngw7617@naver.com")
        );
    }
}
