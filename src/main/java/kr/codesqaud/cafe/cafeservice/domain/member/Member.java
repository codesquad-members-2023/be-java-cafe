package kr.codesqaud.cafe.cafeservice.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Member {

    private Long id;
    private String userName;
    private String password;
    private String email;
}
