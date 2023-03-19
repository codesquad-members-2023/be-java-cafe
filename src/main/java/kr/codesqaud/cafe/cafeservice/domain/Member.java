package kr.codesqaud.cafe.cafeservice.domain;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class Member {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private LocalDateTime createdDate;
    private static final AtomicLong sequence = new AtomicLong(0L);


    public Member(String userName, String password, String email) {
        this.id = idIncrease();
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.createdDate = LocalDateTime.now();
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    private Long idIncrease() {
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
