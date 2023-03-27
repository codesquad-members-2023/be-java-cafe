package kr.codesqaud.cafe.model;

import java.util.List;

public class User {
    private String id;
    private String password;
    private String name;
    private String email;
    private long index;
    private List<Article> articleList;

    public User(String id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }
    public User(String id, String password, String name, String email, int index) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.index = index;
    }

    public boolean validate(String password) {
        return this.password.equals(password);
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
