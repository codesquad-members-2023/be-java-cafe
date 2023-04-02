package kr.codesqaud.cafe.validator;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserSignUpValidator implements Validator {

    private static final int MAX_LENGTH = 30;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (!StringUtils.hasText(user.getUserId())) {
            errors.rejectValue("userId", "required.user.userId", "아이디를 입력해주세요.");
        }
        if (!StringUtils.hasText(user.getPassword())) {
            errors.rejectValue("password", "required.user.password", "패스워드를 입력해주세요.");
        }
        if (!StringUtils.hasText(user.getName())) {
            errors.rejectValue("name", "required.user.name", "이름을 입력해주세요.");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            errors.rejectValue("email", "required.user.email", "이메일을 입력해주세요");
        }

        if (user.getUserId().length() >= MAX_LENGTH) {
            errors.rejectValue("userId", "over.user.userId", "아이디는 30자 미만으로 작성해주세요.");
        }
        if (user.getPassword().length() >= MAX_LENGTH) {
            errors.rejectValue("password", "over.user.password", "패스워드는 30자 미만으로 작성해주세요.");
        }
        if (user.getName().length() >= MAX_LENGTH) {
            errors.rejectValue("name", "over.user.name", "이름은 30자 미만으로 작성해주세요.");
        }
        if (user.getEmail().length() >= MAX_LENGTH) {
            errors.rejectValue("email", "over.user.email", "이메일은 30자 미만으로 작성해주세요.");
        }

        if (!user.getPassword().matches(".*[!@#$%^&*].*")) {
            errors.rejectValue("password", "invalid.user.password", "비밀번호는 특수문자중 하나 이상을 포함해야 합니다.");
        }
        if (!user.getEmail().matches("[^@]+@[^@]+\\.[^@]+")) {
            errors.rejectValue("email", "invalid.user.email", "유효하지 않은 이메일입니다.");
        }
    }

}
