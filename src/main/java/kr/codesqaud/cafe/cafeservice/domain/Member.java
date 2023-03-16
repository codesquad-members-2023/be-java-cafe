package kr.codesqaud.cafe.cafeservice.domain;

import java.util.concurrent.atomic.AtomicLong;

public class Member {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private static AtomicLong sequence = new AtomicLong(0L);

    public Member(Long id, String userName, String password, String email) {
        this.id = idIncrease();
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    private synchronized Long idIncrease() {
        return sequence.incrementAndGet();
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
