package kr.codesqaud.cafe.domain;

public class User {
    private long id;
    private String userId;
    private String password;
    private String name;
    private String email;

    public long getUserNum() {
        return id;
    }

    public void setUserNum(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isIdEquals(String userId) {
        return this.userId.equals(userId);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
