package kr.codesqaud.cafe.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired
	NamedJdbcTemplateMemberRepository repository;

	@Test
	@DisplayName("회원 가입 테스트")
	void save() {
		Member member1 = new Member("member1", "1234", "멤버1", "member1@naver.com");
		repository.save(member1);

		Member findMember = repository.findById(member1.getUserId()).orElseThrow();

		assertThat(member1.getName()).isEqualTo(findMember.getName());
		assertThat(member1.getPassword()).isEqualTo(findMember.getPassword());
		assertThat(member1.getEmail()).isEqualTo(findMember.getEmail());
		assertThat(member1.getUserId()).isEqualTo(findMember.getUserId());
	}
}
