package kr.codesqaud.cafe.model;

import java.time.LocalDateTime;

public class Article {
    private long id;
    private String userId;
    private String title;
    private String contents;
    private LocalDateTime creationTime;

    public Article(String userId, String title, String contents, LocalDateTime creationTime) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.creationTime = creationTime;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

}
