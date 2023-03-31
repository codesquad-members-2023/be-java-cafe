package kr.codesqaud.cafe.dto.reply;

import java.sql.Timestamp;

public class ReplySaveDTO {

    private int articleId;
    private String userId;
    private String contents;
    private Timestamp timestamp;

    public ReplySaveDTO(int articleId, String userId, String contents, Timestamp timestamp) {
        this.articleId = articleId;
        this.userId = userId;
        this.contents = contents;
        this.timestamp = timestamp;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
