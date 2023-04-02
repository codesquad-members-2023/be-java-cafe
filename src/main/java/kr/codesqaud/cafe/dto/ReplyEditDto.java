package kr.codesqaud.cafe.dto;

public class ReplyEditDto {

	private String contents;
	private Long userSequence;
	private Long replySequence;

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Long getUserSequence() {
		return userSequence;
	}

	public void setUserSequence(Long userSequence) {
		this.userSequence = userSequence;
	}

	public Long getReplySequence() {
		return replySequence;
	}

	public void setReplySequence(Long replySequence) {
		this.replySequence = replySequence;
	}
}
