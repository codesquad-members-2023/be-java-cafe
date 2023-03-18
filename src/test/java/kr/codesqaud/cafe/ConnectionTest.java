package kr.codesqaud.cafe;

import kr.codesqaud.cafe.repository.DBConnectionUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionTest {

    @Test
    void connection() {
        DBConnectionUtil connection = new DBConnectionUtil();
        Connection con = connection.getConnection();

        Assertions.assertThat(con).isNotNull();
    }
}
