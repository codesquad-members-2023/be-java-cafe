package kr.codesqaud.cafe.model;

import java.time.LocalDateTime;

public class Reply {
    private long id;
    private String userId;
    private String contents;
    private LocalDateTime creationTime;
    private long articleId;

    public Reply(String userId, String contents, LocalDateTime creationTime, long articleId) {
        this.userId = userId;
        this.contents = contents;
        this.creationTime = creationTime;
        this.articleId = articleId;
    }

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
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
