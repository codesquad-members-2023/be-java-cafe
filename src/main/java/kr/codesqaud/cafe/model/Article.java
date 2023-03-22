package kr.codesqaud.cafe.model;

public class Article {
    private long id;
    private String writer;
    private String title;
    private String contents;

    public Article(long id, String writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article(long id, String writer, String title, String contents, LocalDateTime creationTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.creationTime = creationTime;
    }

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        creationTime = LocalDateTime.now();
    }

    public String getFormattedCreationTime() {
        return creationTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public String getCreationTime() {
        return creationTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public String getFormattedCreationTime() {
        return creationTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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
