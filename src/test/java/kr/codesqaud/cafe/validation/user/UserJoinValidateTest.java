package kr.codesqaud.cafe.validation.user;

import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserJoinValidateTest {

    private final UserJoinValidator validator;
    private final String REQUIRED_USERID = "required.user.userId";
    private final String REQUIRED_PASSWORD = "required.user.password";
    private final String REQUIRED_NAME = "required.user.name";
    private final String REQUIRED_EMAIL = "required.user.email";
    private final String DUPLICATED_USERID = "error.user.duplicatedId";

    @Autowired
    public UserJoinValidateTest(UserJoinValidator validator) {
        this.validator = validator;
    }

    @Test
    @DisplayName("userId가 존재하는 경우 검증")
    void duplicatedUserId() {
        String duplicatedUser = "first";
        User user = new User(duplicatedUser, "password", "name", "email@email");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(DUPLICATED_USERID);
    }

    @Test
    @DisplayName("회원가입 아이디 빈칸 검증")
    void blankUserId() {
        User user = new User("", "password", "name", "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(REQUIRED_USERID);
    }

    @Test
    @DisplayName("비밀번호 빈칸 검증")
    void blankPassword() {
        User user = new User("userID", "", "name", "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(REQUIRED_PASSWORD);
    }

    @Test
    @DisplayName("회원가입 이름 빈칸 검증")
    void blankName() {
        User user = new User("first", "password", "", "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(REQUIRED_NAME);
    }

    @Test
    @DisplayName("회원가입 이메일 빈칸 검증")
    void blankEmail() {
        User user = new User("first", "password", "name", "");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(REQUIRED_EMAIL);
    }
}
