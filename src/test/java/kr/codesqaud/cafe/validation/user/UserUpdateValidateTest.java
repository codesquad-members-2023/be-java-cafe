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

    @Autowired
    public UserUpdateValidateTest(UserUpdateValidator validator) {
        this.validator = validator;
    }

    private final String REQUIRED_USERID = "required.user.userId";
    private final String REQUIRED_PASSWORD = "required.user.password";
    private final String REQUIRED_NAME = "required.user.name";
    private final String REQUIRED_EMAIL = "required.user.email";
    private final String WRONG_PASSWORD = "error.user.password";

    @Test
    @DisplayName("회원정보 수정 비밀번호 빈칸 검증")
    void blankPassword() {
        User user = new User("first", "", "name", "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(REQUIRED_PASSWORD);
    }

    @Test
    @DisplayName("회원정보 수정  이름 빈칸 검증")
    void blankName() {
        User user = new User("first", "password", "", "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(REQUIRED_NAME);
    }

    @Test
    @DisplayName("회원정보 수정  이메일 빈칸 검증")
    void blankEmail() {
        User user = new User("first", "password", "name", "");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(REQUIRED_EMAIL);
    }

    @Test
    @DisplayName("회원정보 수정 올바른 비밀번호 검증")
    void wrongPassword() {
        User user = new User("first", "wrongPassword", "name", "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(WRONG_PASSWORD);
    }
}
