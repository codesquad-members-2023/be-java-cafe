package kr.codesqaud.cafe.domain;

import java.time.LocalDate;
import java.util.Date;

public class Member {
    private String email;
    private String nickName;
    private String password;
    private LocalDate signUpDate;

    public Member(String email, String nickName, String password) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.signUpDate = LocalDate.now();
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }
    public LocalDate getSignUpDate() { return signUpDate; }
}
