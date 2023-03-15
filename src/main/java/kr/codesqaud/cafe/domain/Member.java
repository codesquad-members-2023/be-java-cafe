package kr.codesqaud.cafe.domain;

public class Member {
    private String email;
    private String userNickName;
    private String password;

    public Member(String email, String userNickName, String password) {
        this.email = email;
        this.userNickName = userNickName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public String getPassword() {
        return password;
    }
}
