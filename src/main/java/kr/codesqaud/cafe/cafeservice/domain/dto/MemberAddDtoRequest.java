package kr.codesqaud.cafe.cafeservice.domain.dto;

import kr.codesqaud.cafe.cafeservice.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAddDtoRequest {

    private Long id;
    private String email;
    private String userName;
    private String password;

    public Member toEntity() {
        return new Member(id, userName, email, password);
    }


}
