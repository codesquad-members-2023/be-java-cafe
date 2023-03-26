package kr.codesqaud.cafe.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.cglib.core.Local;

public class Article {
    private long id;
    private User user;
    private String title;
    private String contents;
    private LocalDateTime creationTime;

    public Article(long id, User user, String title, String contents, LocalDateTime creationTime) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.creationTime = creationTime;
    }

    public Article(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        creationTime = LocalDateTime.now();
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getFormattedCreationTime() {
        return creationTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

}
