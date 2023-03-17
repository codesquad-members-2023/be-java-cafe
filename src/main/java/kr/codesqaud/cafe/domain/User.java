package kr.codesqaud.cafe.domain;

public class User {
    private final String password;
    private final String name;
    private final String email;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
