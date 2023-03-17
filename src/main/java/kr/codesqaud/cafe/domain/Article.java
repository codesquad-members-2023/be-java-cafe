package kr.codesqaud.cafe.domain;

import java.time.LocalDate;

public class Article {
    private String writer;
    private String title;
    private String contents;
    private LocalDate registrationDate;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.registrationDate = LocalDate.now();
    }

    public String getAuthor() {
        return writer;
    }

    public void setAuthor(String author) {
        this.writer = author;
    }

    public String getTitle() {
        return title;
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