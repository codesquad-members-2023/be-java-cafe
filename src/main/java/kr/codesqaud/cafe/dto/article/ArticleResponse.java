package kr.codesqaud.cafe.dto.article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleResponse {
    private Long articleIndex;
    private String title;
    private String contents;
    private LocalDateTime articleCreatedDate;
    private Long writerIndex;
    private String nickname;

    public Long getArticleIndex() {
        return articleIndex;
    }

    public void setArticleIndex(Long articleIndex) {
        this.articleIndex = articleIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getArticleCreatedDate() {
        return articleCreatedDate;
    }

    public String getFormattedArticleCreatedDate() {
        return articleCreatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public void setArticleCreatedDate(LocalDateTime articleCreatedDate) {
        this.articleCreatedDate = articleCreatedDate;
    }

    public Long getWriterIndex() {
        return writerIndex;
    }

    public void setWriterIndex(Long writerIndex) {
        this.writerIndex = writerIndex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
