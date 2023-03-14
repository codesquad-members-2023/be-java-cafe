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
    private static long sequence = 0L; //static 사용

    public Member() {
    }

    public Member(String userName, String password, String email) {
        this.id = idIncrease();
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    private Long idIncrease() {
        return ++sequence;
    }
}
