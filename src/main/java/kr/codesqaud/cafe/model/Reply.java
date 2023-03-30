package kr.codesqaud.cafe.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Reply {
    private String userId;
    private String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime creationTime;
    private long articleId;

    public Reply(String userId, String contents, LocalDateTime creationTime, long articleId) {
        this.userId = userId;
        this.contents = contents;
        this.creationTime = creationTime;
        this.articleId = articleId;
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
