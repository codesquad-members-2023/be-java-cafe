package kr.codesqaud.cafe.model;

import java.time.LocalDateTime;

public class Reply {
    private long id;
    private User user;
    private String contents;
    private LocalDateTime creationTime;
    private long articleId;

    public Reply(User user, String contents, LocalDateTime creationTime, long articleId) {
        this.user = user;
        this.contents = contents;
        this.creationTime = creationTime;
        this.articleId = articleId;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public long getArticleId() {
        return articleId;
    }
}
