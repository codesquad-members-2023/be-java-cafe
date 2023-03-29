package kr.codesqaud.cafe.model;

public class ArticleReplyCountsDto {
    private long id;
    private String writer;
    private String title;
    private String contents;
    private String formattedCreationTime;
    private int repliesCount;



    public ArticleReplyCountsDto() {

    }
    public ArticleReplyCountsDto(long id, String writer, String title, String contents, String formattedCreationTime, int repliesCount) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.formattedCreationTime = formattedCreationTime;
        this.repliesCount = repliesCount;
    }

    public int getRepliesCount() {
        return repliesCount;
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
