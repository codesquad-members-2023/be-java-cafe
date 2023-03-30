package kr.codesqaud.cafe.domain.login;

import kr.codesqaud.cafe.domain.user.Member;
import kr.codesqaud.cafe.dto.MemberLoginDto;
import kr.codesqaud.cafe.repository.NamedJdbcTemplateMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

	private final NamedJdbcTemplateMemberRepository namedJdbcTemplateMemberRepository;

	@Autowired
	public LoginService(NamedJdbcTemplateMemberRepository namedJdbcTemplateMemberRepository) {
		this.namedJdbcTemplateMemberRepository = namedJdbcTemplateMemberRepository;
	}

	public <Optional> Member login(MemberLoginDto memberLoginDto) {
		return namedJdbcTemplateMemberRepository.findById(memberLoginDto.getUserId())
			.filter(member -> member.getPassword().equals(memberLoginDto.getPassword()))
			.orElse(null);
	}
}
