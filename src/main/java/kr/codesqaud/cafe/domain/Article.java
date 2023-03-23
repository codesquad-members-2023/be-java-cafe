package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.repository.ArticleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private long articleId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime registrationDate;
    private String formattedRegistrationDate;

    public Article() {
        this.registrationDate = LocalDateTime.now();
    }

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.registrationDate = LocalDateTime.now();
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String author) {
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

    public long getId() {
        return articleId;
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

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
        this.formattedRegistrationDate = registrationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd E요일 HH:mm:ss"));
    }

    public String getFormattedRegistrationDate() {
        return formattedRegistrationDate;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", registrationDate=" + registrationDate +
                ", formattedRegistrationDate='" + formattedRegistrationDate + '\'' +
                '}';
    }
}