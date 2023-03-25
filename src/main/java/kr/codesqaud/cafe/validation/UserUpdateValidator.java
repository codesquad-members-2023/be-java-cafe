package kr.codesqaud.cafe.validation;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserUpdateValidator implements Validator {
    
    private final MemberRepository memberRepository;

    @Autowired
    public UserUpdateValidator(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        User berforeUser = memberRepository.findById(user.getUserId());

        if (!StringUtils.hasText(user.getPassword())) {
            errors.rejectValue("password", "required.user.password");
        }
        if (!StringUtils.hasText(user.getName())) {
            errors.rejectValue("name", "required.user.name");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            errors.rejectValue("email", "required.user.email");
        }
        if (!berforeUser.getPassword().equals(user.getPassword())) {
            errors.rejectValue("password", "error.user.password");
        }
    }
}
