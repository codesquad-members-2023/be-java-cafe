package kr.codesqaud.cafe.domain;

public class User {

    private final int id;
    private final String user_id;
    private final String password;
    private final String name;
    private final String email;

    public User(int id, String user_id, String password, String name, String email) {
        this.id = id;
        this.user_id = user_id;
        this.password = password;
        this.name = name;
        this.email = email;
    }
}
