package kr.codesqaud.cafe.domain.user;

import kr.codesqaud.connection.DBConnectionUtility;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class MemberRepository {

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

	public List<Member> showAllUsers() throws SQLException {
		String sql = "select * from member";

		List<Member> allMembers = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				Member member = new Member();
				member.setUserSequence(resultSet.getLong("member_number"));
				member.setUserId(resultSet.getString("member_id"));
				member.setPassword(resultSet.getString("member_password"));
				member.setName(resultSet.getString("member_name"));
				member.setEmail(resultSet.getString("member_email"));
				allMembers.add(member);
			}
			return allMembers;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(con, pstmt, resultSet);
		}
	}

	public Member findById(String memberId) throws SQLException {
		String sql = "select * from member where member_id = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);

			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				Member member = new Member();
				member.setUserId(resultSet.getString("member_id"));
				member.setPassword(resultSet.getString("member_password"));
				member.setName(resultSet.getString("member_name"));
				member.setEmail(resultSet.getString("member_email"));
				return member;
			} else {
				throw new NoSuchElementException("member not found memberId=" + memberId);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			close(con, pstmt, resultSet);
		}
	}

	public void update(String memberId, Member updateParam) throws SQLException {
		Member member = findById(memberId);
		String originalPassword = member.getPassword();
		if (!originalPassword.equals(updateParam.getPassword())) {
			return;
		}

		String sql = "update member set (member_name, member_email) = (?, ?) where member_id = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, updateParam.getName());
			pstmt.setString(2, updateParam.getEmail());
			pstmt.setString(3, memberId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			close(con, pstmt, null);
		}

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
