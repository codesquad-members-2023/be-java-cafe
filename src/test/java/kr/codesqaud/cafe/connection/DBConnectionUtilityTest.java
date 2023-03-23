package kr.codesqaud.cafe.connection;

import kr.codesqaud.connection.DBConnectionUtility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.*;

public class DBConnectionUtilityTest {

	@Test
	@DisplayName("DB와 잘 연결이 되는지 확인하는 테스트")
	void connection() {
		Connection connection = DBConnectionUtility.getConnection();
		assertThat(connection).isNotNull();
	}
}
