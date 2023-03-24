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

	@Test
	@DisplayName("ID로 회원을 찾는 테스트")
	void findByID() {
		String userId1 = "rhrjsgh97";
		String userId2 = "noeseyhoes";
		Member findMember1 = repository.findById(userId1).orElseThrow();
		Member findMember2 = repository.findById(userId2).orElseThrow();

		assertThat(findMember1.getUserId()).isEqualTo(userId1);
		assertThat(findMember2.getUserId()).isEqualTo(userId2);
	}

}
