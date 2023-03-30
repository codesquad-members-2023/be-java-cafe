package kr.codesqaud.cafe.model;

public class ArticleDto {
    private long id;
    private String writer;
    private String title;
    private String contents;
    private String formattedCreationTime;

    public ArticleDto() {
    }

    public ArticleDto(long id, String writer, String title, String contents, String formattedCreationTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.formattedCreationTime = formattedCreationTime;
    }

    public String getFormattedCreationTime() {
        return formattedCreationTime;
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
