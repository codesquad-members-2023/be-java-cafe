package kr.codesqaud.cafe.cafeservice.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class Member {
    private Long id;
    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    private String nickName;

    @Email
    private String email;

    public Member() {
    }

    public Member(String userName, String password, String nickName, String email) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
