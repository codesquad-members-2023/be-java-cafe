package kr.codesqaud.cafe.validation;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserLoginDTO;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;

@Component
public class UserLoginValidator implements Validator {

    private final MemberRepository memberRepository;

    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    public UserLoginValidator(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return UserLoginDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserLoginDTO loginUser = (UserLoginDTO) target;

        if (!StringUtils.hasText(loginUser.getUserId())) {
            errors.rejectValue("userId", "required.user.userId");
            if (!StringUtils.hasText(loginUser.getPassword())) {
                errors.rejectValue("password", "required.user.password");
            }else {
                errors.rejectValue("password", "error.user.password");
            }
            log.info("사용자 이름 빈칸 예외처리");
            return;
        }
        if (!StringUtils.hasText(loginUser.getPassword())) {
            errors.rejectValue("password", "required.user.password");
        }

        // 잘못된 아이디 확인하고, 아이디 있을 시 비밀번호 검사
        List<User> usersList = memberRepository.findAll();

        Optional<User> checkId = usersList.stream()
                .filter(user -> user.getUserId().equals(loginUser.getUserId()))
                .findFirst();
        if (checkId.isPresent()) {
            if (!checkId.get().getPassword().equals(loginUser.getPassword())) {
                errors.rejectValue("password", "error.user.password");
            }
        } else {
            errors.rejectValue("userId", "error.user.userId");
        }

    }
}
