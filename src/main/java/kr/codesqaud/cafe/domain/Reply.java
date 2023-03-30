package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reply {
    // 게시글의 id
    // 댓글 id
    // 댓글 작성자
    // 댓글 내용
    // 댓글 작성 시간
    private Long replyId; // pk
    private Long articleId; // fk -> article
    private String replyWriter; // fk -> member
    private String replyContents;
    private LocalDateTime replyTime;

    public Reply(Long replyId, Long articleId, String replyWriter, String replyContents, LocalDateTime replyTime) {
        this.replyId = replyId;
        this.articleId = articleId;
        this.replyWriter = replyWriter;
        this.replyContents = replyContents;
        this.replyTime = replyTime;
    }

    public Long getReplyId() {
        return replyId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getReplyWriter() {
        return replyWriter;
    }

    public String getReplyContents() {
        return replyContents;
    }

    public String getReplyTime() {
        return replyTime.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"));
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public void setReplyWriter(String replyWriter) {
        this.replyWriter = replyWriter;
    }
}
