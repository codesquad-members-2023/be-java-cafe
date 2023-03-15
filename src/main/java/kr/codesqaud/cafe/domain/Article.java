package kr.codesqaud.cafe.domain;

public class Article {
    private User writer;
    private String title;
    private String contents;

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Article(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }
}
