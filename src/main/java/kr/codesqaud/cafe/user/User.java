package kr.codesqaud.cafe.user;

import java.util.Objects;

public class User {
    private String id;
    private String password;
    private String name;
    private String email;
    private long index;
    public User(String id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void validate(String password) {
        if (!this.password.equals(password)) {
            throw new IllegalArgumentException("[ERROR] wrong password");
        }
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
