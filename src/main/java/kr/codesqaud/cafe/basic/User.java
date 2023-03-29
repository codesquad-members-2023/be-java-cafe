package kr.codesqaud.cafe.basic;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {

    }

    public User(UserDTO userDTO) {
        this.userId = userDTO.getUserId();
        this.password = userDTO.getPassword();
        this.name = userDTO.getName();
        this.email = userDTO.getEmail();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public boolean isSamePassword(String password) {
        return this.password.equals(password);
    }
}
