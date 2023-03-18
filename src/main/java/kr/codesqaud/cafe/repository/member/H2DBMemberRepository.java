package kr.codesqaud.cafe.repository.member;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.DBConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
@Primary
public class H2DBMemberRepository implements MemberRepository {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void save(User user) {

        String sql = "insert into member (userId, password, name, email) values(?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;


        try {
            con = connect();
            log.info("get Connection={}, class={}", con, con.getClass());
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            log.info("db error");

        } finally {
            close(con, pstmt, null);
        }

    }

    @Override
    public User findById(String userId) {
        String sql = "select * from member where userid = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = connect();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String userid = rs.getString("userid");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String email = rs.getString("email");
                return new User(userid, password, name, email);
            } else {
                throw new NoSuchElementException("member not found memberId = " + userId);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con, pstmt, rs);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();

        String sql = "select * from member";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = connect();
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String userid = rs.getString("userid");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String email = rs.getString("email");
                User user = new User(userid, password, name, email);
                userList.add(user);
            }

            log.info(userList.get(0).getUserId());
            log.info(userList.get(1).getUserId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con, pstmt, rs);
        }


        return userList;
    }

    @Override
    public void updateUser(String userId, User updateUser) {
        String sql = "update member set name=?, email=? where userid=?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = connect();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, updateUser.getName());
            pstmt.setString(2, updateUser.getEmail());
            pstmt.setString(3, userId);
            int resultSet = pstmt.executeUpdate();
            log.info("resultSet={}", resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Connection connect() {
        DBConnectionUtil dbConnectionUtil = new DBConnectionUtil();
        return dbConnectionUtil.getConnection();
    }

    private void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(pstmt);
        JdbcUtils.closeConnection(con);
    }

}
