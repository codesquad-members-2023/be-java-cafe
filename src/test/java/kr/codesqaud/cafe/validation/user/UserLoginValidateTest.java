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
class UserLoginValidateTest {

    private final UserLoginValidator validator;
    private final String REQUIRED_USERID = "required.user.userId";
    private final String REQUIRED_PASSWORD = "required.user.password";
    private final String WRONG_USERID = "error.user.userId";
    private final String WRONG_PASSWORD = "error.user.password";

    @Autowired
    public UserLoginValidateTest(UserLoginValidator validator) {
        this.validator = validator;
    }

    @Test
    @DisplayName("로그인 아이디 빈칸 검증")
    void blankUserId() {
        UserLoginDTO user = new UserLoginDTO("", "password");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(REQUIRED_USERID);
    }

    @Test
    @DisplayName("로그인 비밀번호 빈칸 검증")
    void blankPassword() {
        UserLoginDTO user = new UserLoginDTO("first", "");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(REQUIRED_PASSWORD);
    }

    @Test
    @DisplayName("로그인 잘못된 아이디 검증")
    void wrongId() {
        UserLoginDTO user = new UserLoginDTO("wrongId", "password");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(WRONG_USERID);
    }

    @Test
    @DisplayName("로그인 잘못된 비밀번호 검증")
    void wrongPassword() {
        UserLoginDTO user = new UserLoginDTO("first", "wrongPassword");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(WRONG_PASSWORD);
    }
}
