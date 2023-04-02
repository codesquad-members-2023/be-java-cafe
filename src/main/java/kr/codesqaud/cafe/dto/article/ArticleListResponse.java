package kr.codesqaud.cafe.dto.article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleListResponse {
    private Long articleId;
    private String title;
    private LocalDateTime createdAt;
    private Long writerId;
    private String writerNickname;
    private Long answerCount;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getFormattedCreatedAt() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:dd"));
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getWriterId() {
        return writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public String getWriterNickname() {
        return writerNickname;
    }

    public void setWriterNickname(String writerNickname) {
        this.writerNickname = writerNickname;
    }

    public Long getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Long answerCount) {
        this.answerCount = answerCount;
    }

    public ArticleListResponse() {
    }

    public ArticleListResponse(Long articleId, String title, LocalDateTime createdAt, Long writerId, String writerNickname, Long answerCount) {
        this.articleId = articleId;
        this.title = title;
        this.createdAt = createdAt;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.answerCount = answerCount;
    }
}
