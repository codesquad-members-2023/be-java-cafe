package kr.codesqaud.cafe.domain.dto;

public class UserUpdateForm {

    private int id;
    private String userId;
    private String oldPassword;
    private String newPassword;
    private String name;
    private String email;

    public UserUpdateForm() {
    }

    public UserUpdateForm(int id, String userId, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getName() {
        return name;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
