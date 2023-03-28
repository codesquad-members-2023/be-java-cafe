package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleWithWriterDto {

	private Long articleSequence;
	private String title;
	private String contents;
	private LocalDateTime writtenTime;
	private Long userSequence;
	private String writer;

	public ArticleWithWriterDto() {
	}

	public ArticleWithWriterDto(Long articleSequence, String title, String contents, LocalDateTime writtenTime,String writer, Long userSequence) {
		this.articleSequence = articleSequence;
		this.title = title;
		this.contents = contents;
		this.writtenTime = writtenTime;
		this.writer = writer;
		this.userSequence = userSequence;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
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

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getWrittenTime() {
		return writtenTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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
