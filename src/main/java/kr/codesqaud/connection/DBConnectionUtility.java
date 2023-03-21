package kr.codesqaud.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static kr.codesqaud.connection.ConnectionConst.*;

public class DBConnectionUtility {

	public static Connection getConnection() {
		try {
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			return connection;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
}
