package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleWithoutContentsDto {

	private Long articleSequence;
	private String title;
	private LocalDateTime writtenTime;
	private String writer;
	private Long userSequence;

	public ArticleWithoutContentsDto() {
	}

	public ArticleWithoutContentsDto(Long articleSequence, String title, LocalDateTime writtenTime, String writer) {
		this.articleSequence = articleSequence;
		this.title = title;
		this.writtenTime = writtenTime;
		this.writer = writer;
	}

	public Long getArticleSequence() {
		return articleSequence;
	}

	public void setArticleSequence(Long articleSequence) {
		this.articleSequence = articleSequence;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWrittenTime() {
		return writtenTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public void setWrittenTime(LocalDateTime writtenTime) {
		this.writtenTime = writtenTime;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Long getUserSequence() {
		return userSequence;
	}

	public void setUserSequence(Long userSequence) {
		this.userSequence = userSequence;
	}
}
