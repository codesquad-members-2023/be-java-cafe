package kr.codesqaud.cafe.domain.user;

import kr.codesqaud.cafe.repository.NamedJdbcTemplateMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired
	NamedJdbcTemplateMemberRepository repository;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	@DisplayName("회원 가입 테스트")
	void save() {
		Member test = new Member("test", "1234", "테스트", "test@test.com");
		repository.save(test);

		Member findMember = repository.findById(test.getUserId()).orElseThrow();

		logger.info("test.getName():{}, findMember.getName():{}", test.getName(), findMember.getName());
		logger.info("test.getPassword():{}, findMember.getPassword():{}", test.getPassword(), findMember.getPassword());
		logger.info("test.getEmail():{}, findMember.getEmail():{}", test.getEmail(), findMember.getEmail());
		logger.info("test.getUserId():{}, findMember.getUserId():{}", test.getUserId(), findMember.getUserId());

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

		logger.info("findMember1.getUserId():{}, userId1:{}", findMember1.getUserId(), userId1);
		logger.info("findMember2.getUserId():{}, userId2:{}", findMember2.getUserId(), userId2);

		assertThat(findMember1.getUserId()).isEqualTo(userId1);
		assertThat(findMember2.getUserId()).isEqualTo(userId2);
	}

	@Test
	@DisplayName("모든 회원을 조회하는 테스트")
	void showAllUsers() {
		List<Member> allMembers = repository.showAllUsers();

		assertThat(allMembers.get(0).getUserId()).isEqualTo("rhrjsgh97");
		assertThat(allMembers.get(1).getUserId()).isEqualTo("noeseyhoes");
		assertThat(allMembers.size()).isEqualTo(2);
	}

	@Test
	@DisplayName("비밀번호가 일치 시에 회원 정보를 수정하는 테스트")
	void updateSuccessed() {
		Member updateParam = new Member("rhrjsgh97", "1234", "고봉렬", "gobongyeol@gmail.com");

		logger.info("original password:{}, updateParam password:{}", repository.findById(updateParam.getUserId()).orElseThrow().getPassword(), updateParam.getPassword());
		logger.info("original password == updateParam password:{}", repository.findById(updateParam.getUserId()).orElseThrow().getPassword().equals(updateParam.getPassword()));

		repository.update(updateParam);

		Member updatedMember = repository.findById("rhrjsgh97").orElseThrow();
		assertThat(updatedMember.getName()).isEqualTo(updateParam.getName());
		assertThat(updatedMember.getEmail()).isEqualTo(updateParam.getEmail());
	}

	@Test
	@DisplayName("비밀번호 불일치 시에 회원 정보 수정이 이뤄지지 않는 테스트")
	void updateFailed() {
		Member updateParam = new Member("rhrjsgh97", "5678", "고봉렬", "gobongyeol@gmail.com");

		logger.info("original password:{}, updateParam password:{}", repository.findById(updateParam.getUserId()).orElseThrow().getPassword(), updateParam.getPassword());
		logger.info("original password == updateParam password:{}", repository.findById(updateParam.getUserId()).orElseThrow().getPassword().equals(updateParam.getPassword()));

		repository.update(updateParam);

		Member updatedMember = repository.findById("rhrjsgh97").orElseThrow();
		assertThat(updatedMember.getName()).isNotEqualTo(updateParam.getName());
		assertThat(updatedMember.getEmail()).isNotEqualTo(updateParam.getEmail());
	}
}
