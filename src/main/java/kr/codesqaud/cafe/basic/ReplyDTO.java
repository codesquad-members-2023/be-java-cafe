package kr.codesqaud.cafe.basic;

public class ReplyDTO {

    private int replyId;

    private String writer;

    private String content;

    private String createAt;

    public ReplyDTO(Reply reply) {
        this.replyId = reply.getReplyId();
        this.writer = reply.getWriter();
        this.content = reply.getContent();
        this.createAt = reply.getFormattedLocalDateTime();
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
