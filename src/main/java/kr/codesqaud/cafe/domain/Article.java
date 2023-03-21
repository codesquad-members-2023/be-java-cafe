package kr.codesqaud.cafe.domain;

public class Article {

    private int id;
    private String writer;
    private String title;
    private String contents;

    public Article(String writer, String title, String contents) {
        this.title = title;
        this.writer = writer;
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
