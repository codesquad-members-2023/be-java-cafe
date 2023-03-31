package kr.codesqaud.cafe.domain;

public class User {
    private final String id;
    private final String password;
    private final String name;
    private final String email;

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEqualPasswordTo(String password) {
        return this.password.equals(password);
    }
}
