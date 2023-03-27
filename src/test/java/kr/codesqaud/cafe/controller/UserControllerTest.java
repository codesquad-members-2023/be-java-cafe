package kr.codesqaud.cafe.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.codesqaud.cafe.model.User;
import kr.codesqaud.cafe.repository.UserDto;
import kr.codesqaud.cafe.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    UserController userController;
    @Autowired
    UserService userService;

    @Test
    @DisplayName("새로운 유저 데이터가 정상적으로 들어가야 한다.")
    void creatUser() {
        // given
        final User test = new User("test5", "1234", "tset5", "test5@test.com");
        // when
        userService.createUser(test);
        // then
        UserDto userDto = userService.findUserByUserId("test5");
        assertThat(test.getUserId().equals(userDto.getUserId())).isTrue();
        assertThat(test.getPassword().equals(userDto.getPassword())).isTrue();
        assertThat(test.getName().equals(userDto.getName())).isTrue();
        assertThat(test.getEmail().equals(userDto.getEmail())).isTrue();
    }

    @Test
    @DisplayName("새로운 유저 데이터의 숫자와 반환 리스트의 값이 같아야 한다.")
    void findUserList() {
        // given
        final int userSize = 3;
        final User test1 = new User("test6", "1234", "tset5", "test5@test.com");
        final User test2 = new User("test7", "1234", "tset5", "test5@test.com");
        userService.createUser(test1);
        userService.createUser(test2);
        // when
        List<UserDto> list = userService.findUserAll();
        // then
        assertThat(userSize == list.size()).isTrue();
    }

    @Test
    @DisplayName("유저 id로 유저의 정보를 찾고 Dto에 담아 반환해야 한다.")
    void findUserProfile() {
        // given
        final User test = new User("test5", "1234", "tset5", "test5@test.com");
        userService.createUser(test);
        // when
        UserDto userDto = userService.findUserByUserId("test5");
        // then
        assertThat(userDto.getUserId()).isEqualTo(test.getUserId());
        assertThat(userDto.getPassword()).isEqualTo(test.getPassword());
        assertThat(userDto.getName()).isEqualTo(test.getName());
        assertThat(userDto.getEmail()).isEqualTo(test.getEmail());
    }

    @Test
    @DisplayName("프로필 수정에서 비밀번호가 일치하면 수정이 반영되어야 한다.")
    void updateUserSuccess() {
        // given
        final User test = new User("test5", "1234", "test567", "test567@test.com");
        final UserDto testEdit = new UserDto("test5", "1234", "test56", "test5@test.com");
        userService.createUser(test);
        // when
        userService.updateUser(testEdit);
        // then
        UserDto userDto = userService.findUserByUserId("test5");
        assertThat(userDto.getName()).isEqualTo("test56");
        assertThat(userDto.getEmail()).isEqualTo("test5@test.com");
    }

    @Test
    @DisplayName("프로필 수정에서 비밀번호가 일치하지 않으면 수정이 반영되지 않아야 한다.")
    void updateUserFail() {
        // given
        final User test = new User("test5", "1234", "test567", "test567@test.com");
        final UserDto testEdit = new UserDto("test5", "12345", "test56", "test5@test.com");
        userService.createUser(test);
        // when
        userService.updateUser(testEdit);
        // then
        UserDto userDto = userService.findUserByUserId("test5");
        assertThat(userDto.getName()).isEqualTo("test567");
        assertThat(userDto.getEmail()).isEqualTo("test567@test.com");
    }

    @Test
    @DisplayName("아이디와 비밀번호가 일치해 로그인 성공시 세션값이 존재해야 한다.")
    void loginSuccess() {
        // given
        final User test = new User("test5", "12345", "test56", "test5@test.com");
        final UserDto testLoginDto = new UserDto("test5", "12345", "test56", "test5@test.com");
        userService.createUser(test);
        MockHttpSession mockHttpSession = new MockHttpSession();
        // when
        userController.loginSubmit(testLoginDto, mockHttpSession);
        // then
        assertThat(mockHttpSession.getAttribute("userId")).isEqualTo("test5");
    }

    @Test
    @DisplayName("아이디와 비밀번호가 일치하지 않아 로그인 실패시 세션값이 존재하지 않아야 한다.")
    void loginFail() {
        // given
        final User test = new User("test5", "12345", "test56", "test5@test.com");
        final UserDto testLoginDto = new UserDto("test5", "123456", "test56", "test5@test.com");
        userService.createUser(test);
        MockHttpSession mockHttpSession = new MockHttpSession();
        // when
        userController.loginSubmit(testLoginDto, mockHttpSession);
        // then
        assertThat(mockHttpSession.getAttribute("userId")).isEqualTo(null);
    }

    @Test
    @DisplayName("로그아웃시 세션값이 지워져야 한다.")
    void logOut() {
        // given
        final UserDto testLoginDto = new UserDto("test5", "123456", "test56", "test5@test.com");
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userId", testLoginDto.getUserId());
        // when
        userController.logOut(mockHttpSession);
        // then
        assertThat(mockHttpSession.isInvalid()).isTrue();
    }
}
