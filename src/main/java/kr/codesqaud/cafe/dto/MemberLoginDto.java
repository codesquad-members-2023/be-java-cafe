package kr.codesqaud.cafe.dto;

public class MemberLoginDto {

	private String userId;
	private String password;
	private Long userSequence;

	public MemberLoginDto(Long userSequence, String userId, String password) {
		this.userSequence = userSequence;
		this.userId = userId;
		this.password = password;
	}

	public Long getUserSequence() {
		return userSequence;
	}

	public void setUserSequence(Long userSequence) {
		this.userSequence = userSequence;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
