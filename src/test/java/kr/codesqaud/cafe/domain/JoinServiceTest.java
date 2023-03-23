package kr.codesqaud.cafe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import kr.codesqaud.cafe.utils.UserInfoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import kr.codesqaud.cafe.repository.JdbcUserRepository;
import kr.codesqaud.cafe.service.JoinService;
import kr.codesqaud.cafe.service.impl.JoinServiceImpl;
import kr.codesqaud.cafe.model.User;

@SpringBootTest
class JoinServiceTest {
    private DataSource dataSource;
    private JoinService joinService;
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        dataSource = new DriverManagerDataSource("jdbc:h2:mem:test", "sa", "");
        joinService = new JoinServiceImpl(new JdbcUserRepository(dataSource));
        jdbcTemplate = new JdbcTemplate(dataSource);

        User user1 = new User("conux", "asd", "J", "ho@naver.com");
        User user2 = new User("tonux", "asd", "Js", "ho2@naver.com");
        joinService.join(user1);
        joinService.join(user2);
    }

    @AfterEach
    public void clean() {
        jdbcTemplate.update("delete from users; alter table users alter column idx restart with 1;");
    }

    @Test
    @DisplayName("새로운 User 객체를 인자로 받아 회원 Repository에 등록합니다.")
    void join() {
        //when
        joinService.join(new User("honux", "asd", "jeong", "ho@naver.com"));
        //then
        assertThat(joinService.lookupUser("honux").getName()).isEqualTo("jeong");
    }

    @Test
    @DisplayName("UserId가 일치하는 회원을 Repository에서 조회합니다.")
    void lookupUser() {
        //then
        assertThat(joinService.lookupUser("conux").getName()).isEqualTo("J");
    }

    @Test
    @DisplayName("모든 회원을 Repository에서 조회한다.")
    void lookupAllUsers() {

        //then
        assertThat(joinService.lookupAllUser().get(0).getId()).isEqualTo("conux");
    }

    @Test
    @DisplayName("비밀번호가 일치하면 회원 정보를 수정할 수 있다.")
    void updateUserWithCorrectPassword() {
        joinService.updateUser("conux", "asd", "skarnjsdn1", "Jayho", "ngw7617@naver.com");
        User foundUser = joinService.lookupUser("conux");
        //then
        assertAll(
                () -> assertThat(foundUser.getName()).isEqualTo("Jayho"),
                () -> assertThat(foundUser.getPassword()).isEqualTo("skarnjsdn1"),
                () -> assertThat(foundUser.getEmail()).isEqualTo("ngw7617@naver.com")
        );
    }

    @Test
    @DisplayName("비밀번호가 틀리면 예외를 발생시킨다.")
    void updateUserWithWrongPassword() {
        //when, then
        assertThatThrownBy(() -> {
            joinService.updateUser("conux", "asd123", "skarnjsdn1", "Jayho", "ngw7617@naver.com");
        }).isInstanceOf(UserInfoException.class);
    }

}
