package kr.codesqaud.cafe.cafeservice.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Reply {
    private Long id;
    private Long member_id;
    private Long article_id;
    private String content;
    private String nickName;
    private LocalDateTime createdDate;

    public Reply(Long member_id, Long article_id, String content, String nickName) {
        this.member_id = member_id;
        this.article_id = article_id;
        this.content = content;
        this.nickName = nickName;
    }

    public Reply() {
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMember_id() {
        return member_id;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }

    public Long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", member_id=" + member_id +
                ", article_id=" + article_id +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
