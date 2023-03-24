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
		Member test = new Member("test", "1234", "테스트", "test@test.com");
		repository.save(test);

		Member findMember = repository.findById(test.getUserId()).orElseThrow();

		assertThat(test.getName()).isEqualTo(findMember.getName());
		assertThat(test.getPassword()).isEqualTo(findMember.getPassword());
		assertThat(test.getEmail()).isEqualTo(findMember.getEmail());
		assertThat(test.getUserId()).isEqualTo(findMember.getUserId());
	}
}
