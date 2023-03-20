package kr.codesqaud.cafe.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Article {
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime registrationDate;
    private int articleId;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.registrationDate = LocalDateTime.now();
    }

    public String getAuthor() {
        return writer;
    }

    public void setAuthor(String author) {
        this.writer = author;
    }

    public void setArticleId(int id) {
        articleId = id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public int getId() {
        return articleId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return contents;
    }

    public void setContent(String contents) {
        this.contents = contents;
    }
}