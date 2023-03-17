package kr.codesqaud.cafe.model;

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

    public void updateUserInfo(String name, String password, String email) {
        this.name = name;
        this.password = password;
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
