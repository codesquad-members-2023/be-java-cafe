package kr.codesqaud.cafe.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

    Logger log = LoggerFactory.getLogger(getClass());

    public Connection getConnection() {
        final String URL = "jdbc:h2:/Users/birdie/Desktop/h2/test";
        final String USERNAME = "sa";
        final String PASSWORD = "1234";

        try {
            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info(String.valueOf(con.getClass()));
            return con;
        } catch (SQLException e) {
            log.info("con error");
            throw new IllegalArgumentException(e);
        }
    }
}
