package kr.codesqaud.cafe.dto.answer;

import kr.codesqaud.cafe.domain.Answer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AnswerResponseDto {
    private long answerId;
    private String contents;
    private long writerIndex;
    private String userId;
    private String nickname;
    private LocalDateTime createdDate;

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getWriterIndex() {
        return writerIndex;
    }

    public void setWriterIndex(long writerIndex) {
        this.writerIndex = writerIndex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getFormattedCreatedDate() {
        return this.createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
    }

    public static AnswerResponseDto toDto(Answer answer) {
        AnswerResponseDto answerDBDto = new AnswerResponseDto();
        answerDBDto.setAnswerId(answer.getId());
        answerDBDto.setContents(answer.getContents());
        answerDBDto.setWriterIndex(answer.getWriterId());
        answerDBDto.setNickname(answer.getWriterNickname());
        answerDBDto.setCreatedDate(answer.getCreatedDate());
        return answerDBDto;
    }
}
