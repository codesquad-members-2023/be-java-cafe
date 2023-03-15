package kr.codesqaud.cafe.domain;

public class Member {
    private String email;
    private String userId;
    private String password;

    public Member(String email, String userId, String password) {
        this.email = email;
        this.userId = userId;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
