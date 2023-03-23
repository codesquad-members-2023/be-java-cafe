package kr.codesqaud.cafe.validation;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final MemberRepository memberRepository;

    @Autowired
    public UserValidator(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User loginUser = (User) target;
        if (!StringUtils.hasText(loginUser.getUserId())) {
            errors.rejectValue("userId", "required.user.userId","아이디를 입력해주세요" );
        }

        if (!StringUtils.hasText(loginUser.getPassword())) {
            errors.rejectValue("password", "required.user.password", "비밀번호를 입력해주세요");
        }
        if (StringUtils.hasText(loginUser.getUserId())) {
            User originUer = memberRepository.findById(loginUser.getUserId());
            if (!originUer.getPassword().equals(loginUser.getPassword())) {
                errors.rejectValue("password", "error.user.password", "잘못된 비밀번호 입력입니다.");
            }
        }
    }
}
