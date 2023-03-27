package kr.codesqaud.cafe.repository;

public class ArticleDto {
    private int articleId;
    private String userId;
    private String title;
    private String contents;
    private String time;
    private boolean deleted;

    public ArticleDto() {
    }

    public ArticleDto(int articleId, String userId, String title, String contents, String time, boolean deleted) {
        this.articleId = articleId;
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.time = time;
        this.deleted = deleted;
    }

    public int getArticleId() {
        return articleId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getTime() {
        return time;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
                "articleId=" + articleId +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", time='" + time + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
