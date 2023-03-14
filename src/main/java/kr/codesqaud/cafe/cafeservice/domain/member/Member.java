package kr.codesqaud.cafe.cafeservice.domain.member;

import lombok.Getter;

@Getter
public class Member {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private static long sequence = 0L; //static 사용

    public Member(Long id, String userName, String password, String email) {
        this.id = idIncrease();
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    private Long idIncrease() {
        return ++sequence;
    }
}
