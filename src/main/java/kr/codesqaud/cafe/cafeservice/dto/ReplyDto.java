package kr.codesqaud.cafe.cafeservice.dto;

import kr.codesqaud.cafe.cafeservice.domain.Reply;

import java.time.LocalDateTime;

public class ReplyDto {

    private Long id;
    private Long article_id;
    private Long member_id;
    private String content;
    private String nickname;
    private LocalDateTime createdDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }

    public Long getMember_id() {
        return member_id;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public static ReplyDto toDto(Reply reply) {
        ReplyDto replyToDo = new ReplyDto();
        replyToDo.setId(reply.getId());
        replyToDo.setMember_id(reply.getMember_id());
        replyToDo.setArticle_id(reply.getArticle_id());
        replyToDo.setContent(reply.getContent());
        replyToDo.setNickname(reply.getNickName());
        replyToDo.setCreatedDate(reply.getCreatedDate());
        return replyToDo;
    }
}
