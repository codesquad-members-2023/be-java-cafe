package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;

public class ArticleWithWriterDto {

	private Long articleSequence;
	private String title;
	private String contents;
	private LocalDateTime writtenTime;
	private Long userSequence;

	public ArticleWithWriterDto(Long articleSequence, String title, String contents, LocalDateTime writtenTime, Long userSequence) {
		this.articleSequence = articleSequence;
		this.title = title;
		this.contents = contents;
		this.writtenTime = writtenTime;
		this.userSequence = userSequence;
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

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public LocalDateTime getWrittenTime() {
		return writtenTime;
	}

	public void setWrittenTime(LocalDateTime writtenTime) {
		this.writtenTime = writtenTime;
	}

	public Long getUserSequence() {
		return userSequence;
	}

	public void setUserSequence(Long userSequence) {
		this.userSequence = userSequence;
	}
}
