package kr.codesqaud.cafe.cafeservice.domain;

public class Article {
    private String writer;
    private String title;
    private String content;
    private Integer views;

    public Article(String writer, String title, String content, String views) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = 0;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Integer getViews() {
        return views;
    }
}
