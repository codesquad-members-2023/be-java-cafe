package kr.codesqaud.cafe.dto.article;

public class ArticleFormDTO {
    private String userId;
    private String title;
    private String contents;

    public ArticleFormDTO() {}

    public ArticleFormDTO(String userId, String title, String contents) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
