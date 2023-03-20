package kr.codesqaud.cafe.domain;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Article {
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private Timestamp createdDate;
    private Timestamp updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
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

    public String getCreatedDate() {
        return createdDate.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return createdDate.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Article() {
    }

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public boolean equals(Long articleId) {
        return this.id.equals(articleId);
    }

}
