package kr.codesqaud.cafe.domain.article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reply {
    private Long id;
    private long articleId;
    private Writer writer;
    private String contents;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public long getArticleId() {
        return articleId;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getFormattedCreatedDate() {
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public String getFormattedUpdatedDate() {
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Reply() {}

    public boolean equals(long answerId) {
        return this.id == answerId;
    }

    public long getWriterId() {
       return writer.getId();
    }

    public String getWriterNickname() {
        return writer.getNickname();
    }
}
