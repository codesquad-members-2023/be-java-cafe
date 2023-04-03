package kr.codesqaud.cafe.model;

public class ArticleReplyCountsDto {
    private long id;
    private String writer;
    private String title;
    private String formattedCreationTime;
    private int repliesCount;

    public ArticleReplyCountsDto(long id, String writer, String title, String formattedCreationTime, int repliesCount) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.formattedCreationTime = formattedCreationTime;
        this.repliesCount = repliesCount;
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

    public String getFormattedCreationTime() {
        return formattedCreationTime;
    }

    public int getRepliesCount() {
        return repliesCount;
    }

}
