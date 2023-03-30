package kr.codesqaud.cafe.cafeservice.session;

public class LoginSessionUtils {
    private Long id;
    private String password;

    public LoginSessionUtils(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public static void sessionCheckId(Long id, LoginSessionUtils sessionUtils) {
        if (sessionUtils.getId() != id) {
            throw new NullPointerException();
        }
    }

    @Override
    public String toString() {
        return "LoginSessionUtils{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
