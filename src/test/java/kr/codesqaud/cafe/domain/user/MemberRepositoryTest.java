package kr.codesqaud.cafe.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryTest {

	MemberRepository repository = new MemberRepository();

	@Test
	void crud() throws SQLException {
		// save
		Member member1 = new Member("rhrjsgh97", "9707", "고건호", "rhrjsgh97@gmail.com");
		Member member2 = new Member("noeseyhoes", "7290", "서혜선", "hseon0927@gmail.com");
		Member member3 = new Member("flaehdan", "1234", "임동현", "flaehdan@gmail.com");
		Member member4 = new Member("roy", "1234", "이승로", "roy@gmail.com");
		Member member5 = new Member("hana", "1234", "왕하나", "hana@naver.com");
		repository.save(member1);
		repository.save(member2);
		repository.save(member3);
		repository.save(member4);
		repository.save(member5);

		// findById
		Member findMember1 = repository.findById(member1.getUserId());
		log.info("findMember1={}", findMember1);
		Member findMember2 = repository.findById(member2.getUserId());
		log.info("findMember2={}", findMember2);
		Member findMember3 = repository.findById(member3.getUserId());
		log.info("findMember3={}", findMember3);
		Member findMember4 = repository.findById(member4.getUserId());
		log.info("findMember4={}", findMember4);
		Member findMember5 = repository.findById(member5.getUserId());
		log.info("findMember5={}", findMember5);

		assertThat(findMember1.getUserId()).isEqualTo(member1.getUserId());
		assertThat(findMember1.getPassword()).isEqualTo(member1.getPassword());
		assertThat(findMember1.getName()).isEqualTo(member1.getName());
		assertThat(findMember1.getEmail()).isEqualTo(member1.getEmail());

		assertThat(findMember2.getUserId()).isEqualTo(member2.getUserId());
		assertThat(findMember2.getPassword()).isEqualTo(member2.getPassword());
		assertThat(findMember2.getName()).isEqualTo(member2.getName());
		assertThat(findMember2.getEmail()).isEqualTo(member2.getEmail());

		// update: name: 서혜선 -> 서혜뭉
		repository.update(member2.getUserId(), new Member("noeseyhoes", "7290", "서혜뭉", "hseon0927@gmail.com"));
		Member updatedMember1 = repository.findById(member2.getUserId());
		assertThat(updatedMember1.getName()).isEqualTo("서혜뭉");

		// update fail: name: 고건호 -> 고봉렬
		repository.update(member1.getUserId(), new Member("rhrjsgh97", "1234", "고봉렬", "rhrjsgh97@gmail.com"));
		Member updatedMember2 = repository.findById(member1.getUserId());
		assertThat(updatedMember2.getName()).isEqualTo("고건호");

		// showAll
		List<Member> allMembers = repository.showAllUsers();
		log.info("allMembers.size():{}", allMembers.size());
		for (int i = 0; i < allMembers.size(); i++) {
			log.info("이름:{}", allMembers.get(i).getUserId());
			log.info("ID:{}", allMembers.get(i).getName());
			log.info("PW:{}", allMembers.get(i).getPassword());
			log.info("email:{}", allMembers.get(i).getEmail());
		}
	}
}
