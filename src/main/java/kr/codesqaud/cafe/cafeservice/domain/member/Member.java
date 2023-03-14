package kr.codesqaud.cafe.cafeservice.domain.member;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Member {

    private Long id;
    private String userName;
    private String password;
    private String email;
    private String phone;

    public Member() {
    }

    public Member(String userName, String password, String email, String phone) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}
