package kr.codesqaud.cafe.domain;


import java.util.Objects;

public class Member {
    private String userId;
    private String password;
    private String userName;
    private String email;

    public Member(String userId, String password, String userName, String email) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return userId.equals(member.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
