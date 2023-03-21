package kr.codesqaud.cafe.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Member {

    private long id;
    private String email;
    private String nickName;
    private String password;
    private LocalDateTime signUpDate;

    public Member() {
        this.signUpDate = LocalDateTime.now();
    }

    public Member(String email, String nickName, String password) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.signUpDate = LocalDateTime.now();
    }

    public long getId() {
        return id;
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
    public LocalDateTime getSignUpDate() { return signUpDate; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSignUpDate(LocalDateTime signUpDate) {
        this.signUpDate = signUpDate;
    }
}
