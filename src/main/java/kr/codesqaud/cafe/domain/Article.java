package kr.codesqaud.cafe.domain;

public class Article {
    private final String writer;
    private final String title;
    private final String content;
    private int index;

    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Article(String writer, String title, String content, int index) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.index = index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public int getIndex() {
        return index;
    }
}
