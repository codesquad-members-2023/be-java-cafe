package kr.codesqaud.cafe.validation.user;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.user.UserLoginDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserUpdateValidateTest {

    private final UserUpdateValidator validator;
    private TestUserConstant testUserConstant = new TestUserConstant();

    @Autowired
    public UserUpdateValidateTest(UserUpdateValidator validator) {
        this.validator = validator;
    }

    @Test
    @DisplayName("회원정보 수정 비밀번호 빈칸 검증")
    void blankPassword() {
        User user = new User("first", "", "name", "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.REQUIRED_PASSWORD);
    }

    @Test
    @DisplayName("회원정보 수정  이름 빈칸 검증")
    void blankName() {
        User user = new User("first", "password", "", "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.REQUIRED_NAME);
    }

    @Test
    @DisplayName("회원정보 수정  이메일 빈칸 검증")
    void blankEmail() {
        User user = new User("first", "password", "name", "");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.REQUIRED_EMAIL);
    }

    @Test
    @DisplayName("회원정보 수정 올바른 비밀번호 검증")
    void wrongPassword() {
        User user = new User("first", "wrongPassword", "name", "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.WRONG_PASSWORD);
    }

    @Test
    @DisplayName("회원정보 수정 이름 길이 제한")
    void limitNameLength() {
        User user = new User("first", "password", testUserConstant.NAME_LENGTH_OVER, "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.NAME_LENGTH_ERROR);
    }

    @Test
    @DisplayName("회원정보 수정 아이디 길이제한")
    void limitEmailError() {
        User user = new User("first", "password", "name", testUserConstant.EMAIL_LENGTH_OVER);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.EMAIL_LENGTH_ERROR);
    }
}
