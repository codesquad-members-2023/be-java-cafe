package kr.codesqaud.cafe.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import kr.codesqaud.cafe.exceptions.UserInfoException;
import kr.codesqaud.cafe.model.User;

@JdbcTest
@Sql("classpath:test-schema.sql")
class UserRepositoryTest {
    private UserRepository userRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        userRepository = new JdbcUserRepository(jdbcTemplate.getDataSource());

        User user1 = new User("conux", "asd", "J", "ho@naver.com");
        User user2 = new User("tonux", "asd", "Js", "ho2@naver.com");
        userRepository.addUser(user1);
        userRepository.addUser(user2);
    }

    @Test
    @DisplayName("ID가 일치하는 유저를 찾아서 반환한다.")
    void findById() throws UserInfoException {
        User user = userRepository.findById("conux");
        //then
        assertThat(user.getName()).isEqualTo("J");
    }

    @Test
    @DisplayName("유저를 추가한다.")
    void addUser() throws UserInfoException {
        userRepository.addUser(new User("poro", "123", "gwonwoo", "ngw7617@naver.com"));
        //then
        assertThat(userRepository.findById("poro").getName()).isEqualTo("gwonwoo");
    }

    @Test
    @DisplayName("유저의 정보를 업데이트한다.")
    void updateUser() throws UserInfoException {
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
