package kr.codesqaud.cafe.domain.user;

import kr.codesqaud.connection.DBConnectionUtility;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {

	private static final List<Member> userRepository = new ArrayList<>();
//	private static long sequence = 0L;

	public Member save(Member member) throws SQLException {
		String sql = "insert into member(member_id, member_password, member_name, member_email) values (?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());
			pstmt.executeUpdate();
			return member;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(con, pstmt, null);
		}
	}

//		public Member save(Member user) {
//			user.setUserSequence(++sequence);
//			userRepository.add(user);
//			return user;
//		}

	public List<Member> showAllUsers() {
		List<Member> allMembers = new ArrayList<>();
		for (int i = 0; i < userRepository.size(); i++) {
			allMembers.add(userRepository.get(i));
		}
		return allMembers;
	}

	public Member findByUserId(String userId) {
		return userRepository.stream().filter(user -> user.getUserId().equals(userId)).findFirst().orElseThrow();
	}

	public void updateUser(String userId, Member updateParam) {
		Member user = findByUserId(userId);
		String originalPassword = user.getPassword();
		if (!originalPassword.equals(updateParam.getPassword())) {
			return;
		}
		user.setPassword(updateParam.getPassword());
		user.setName(updateParam.getName());
		user.setEmail(updateParam.getEmail());
	}

	private void close(Connection con, Statement stmt, ResultSet resultSet) {

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private static Connection getConnection() {
		return DBConnectionUtility.getConnection();
	}
}
