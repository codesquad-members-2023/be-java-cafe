package kr.codesqaud.cafe.domain.logout;

import kr.codesqaud.cafe.domain.user.Member;
import kr.codesqaud.cafe.repository.NamedJdbcTemplateMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class LogoutService {

	private final NamedJdbcTemplateMemberRepository namedJdbcTemplateMemberRepository;

	@Autowired
	public LogoutService(NamedJdbcTemplateMemberRepository namedJdbcTemplateMemberRepository) {
		this.namedJdbcTemplateMemberRepository = namedJdbcTemplateMemberRepository;
	}

	public void logout(HttpSession httpSession) {

		httpSession.invalidate();
	}
}
