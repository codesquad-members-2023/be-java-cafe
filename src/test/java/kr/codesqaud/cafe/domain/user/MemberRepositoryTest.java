package kr.codesqaud.cafe.domain.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

	MemberRepository repository = new MemberRepository();

	@Test
	void crud() throws SQLException {
		// save
		Member member = new Member("rhrjsgh97", "970710", "고건호", "rhrjsgh97@gmail.com");
		repository.save(member);

		assertThat(member.getName()).isEqualTo("고건호");
	}
}
