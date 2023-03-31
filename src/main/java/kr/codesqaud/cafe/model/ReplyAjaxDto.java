package kr.codesqaud.cafe.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReplyAjaxDto {
    private long replyId;
    private String userId;
    private String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime creationTime;
    private long articleId;

    public ReplyAjaxDto(long replyId, String userId, String contents, LocalDateTime creationTime, long articleId) {
        this.replyId = replyId;
        this.userId = userId;
        this.contents = contents;
        this.creationTime = creationTime;
        this.articleId = articleId;
    }

    public long getReplyId() {
        return replyId;
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
