package kr.codesqaud.cafe.domain;

public class Article {
    private final String writer;
    private final String title;
    private final String content;
    private Long id;

    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Article(String writer, String title, String content, Long index) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.id = index;
    }

    public void setIndex(Long index) {
        this.id = index;
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

    public Long getIndex() {
        return id;
    }
}
