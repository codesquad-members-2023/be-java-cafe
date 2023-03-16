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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User)o;
        return id.equals(user.id) && password.equals(user.password) && name.equals(user.name)
                && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, name, email);
    }
}
