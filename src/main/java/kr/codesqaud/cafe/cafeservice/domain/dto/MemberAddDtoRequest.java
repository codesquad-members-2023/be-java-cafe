package kr.codesqaud.cafe.cafeservice.domain.dto;

import kr.codesqaud.cafe.cafeservice.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberAddDtoRequest {

    private Long id;
    private String email;
    private String userName;
    private String password;
    private static long sequence = 0L; //static 사용

    public Member toEntity() {
        return new Member(idIncrease(), userName, email, password);
    }

    private Long idIncrease() {
        return ++sequence;
    }
}
