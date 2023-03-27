package kr.codesqaud.cafe.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import kr.codesqaud.cafe.service.ReplyService;

public class Reply {
    private int replyId;
    private String articleId;
    private String userId;
    private String contents;
    private String time;
    private boolean deleted;

    public Reply(String userId, String contents, String articleId) {
        this.replyId = ReplyService.getReplySize();
        this.articleId = articleId;
        this.userId = userId;
        this.contents = contents;
        this.time = formattingTime();
        this.deleted = false;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
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
        return "Reply{" +
                "replyId=" + replyId +
                ", articleId='" + articleId + '\'' +
                ", userId='" + userId + '\'' +
                ", contents='" + contents + '\'' +
                ", time='" + time + '\'' +
                ", deleted=" + deleted +
                '}';
    }

    public String formattingTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(Calendar.getInstance().getTime());
    }
}
