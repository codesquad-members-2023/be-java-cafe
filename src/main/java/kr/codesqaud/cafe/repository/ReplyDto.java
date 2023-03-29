package kr.codesqaud.cafe.repository;

public class ReplyDto {
    private int replyId;
    private String userId;
    private String articleId;
    private String contents;
    private String time;
    private boolean deleted;

    public ReplyDto() {
    }

    public ReplyDto(int replyId, String userId, String articleId, String contents, String time, boolean deleted) {
        this.replyId = replyId;
        this.userId = userId;
        this.articleId = articleId;
        this.contents = contents;
        this.time = time;
        this.deleted = deleted;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return time;
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
        return "ReplyDto{" +
                "replyId=" + replyId +
                ", userId='" + userId + '\'' +
                ", articleId='" + articleId + '\'' +
                ", contents='" + contents + '\'' +
                ", time='" + time + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
