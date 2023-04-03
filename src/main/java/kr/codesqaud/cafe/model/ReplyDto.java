package kr.codesqaud.cafe.model;


public class ReplyDto {
    private long id;
    private String writer;
    private String contents;
    private String formattedCreationTime;
    private long articleId;

    public ReplyDto(long id, String writer, String contents, String formattedCreationTime, long articleId) {
        this.id = id;
        this.writer = writer;
        this.contents = contents;
        this.formattedCreationTime = formattedCreationTime;
        this.articleId = articleId;
    }

    public long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public String getFormattedCreationTime() {
        return formattedCreationTime;
    }

    public long getArticleId() {
        return articleId;
    }
}

