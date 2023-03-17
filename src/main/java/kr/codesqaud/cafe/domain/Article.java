package kr.codesqaud.cafe.domain;

import java.util.Date;

public class Article {

    private Long id;
    private String writer;
    private String title;
    private String contents;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

}
