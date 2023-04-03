package kr.codesqaud.cafe.validator;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.domain.dto.LoginForm;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserLoginValidator implements Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserLoginValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginForm loginForm = (LoginForm) target;

        if (!userRepository.findAll().stream().anyMatch(u -> u.getUserId().equals(loginForm.getUserId()))) {
            errors.rejectValue("userId", "nonexistent.loginForm.userId", "존재하지 않는 ID입니다.");
            return;
        }

        User findUser = userRepository.findByUserId(loginForm.getUserId());
        if (!findUser.getPassword().equals(loginForm.getPassword())) {
            errors.rejectValue("password", "wrong.loginForm.password", "비밀번호가 틀렸습니다.");
        }
    }
}
