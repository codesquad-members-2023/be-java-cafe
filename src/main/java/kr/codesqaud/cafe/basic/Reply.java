package kr.codesqaud.cafe.basic;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Reply {
    private int replyId;

    private int articleId;
    private String writer;
    private String content;
    private Timestamp createAt;

    public Reply() {};

    public Reply(int articleId, String writer, String content) {
        this.articleId = articleId;
        this.writer = writer;
        this.content = content;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public String getFormattedTime() {
        return createAt.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }
}
