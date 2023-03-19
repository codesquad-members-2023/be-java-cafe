package kr.codesqaud.cafe.repository.h2Repository;

import kr.codesqaud.cafe.basic.Article;
import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static kr.codesqaud.cafe.config.repositoryConfig.ConnectionConfig.*;


@Repository
@Qualifier
public class H2UserRepository implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger("user database");

    @Autowired
    public H2UserRepository() {};

    @Override
    public void join(User user) {
        String sql = "insert into users(userId, password, name, email) values (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            // TODO : 어떤 예외 처리가 필요할까 고민해보기
            logger.error("Failed to add of DB :" + e);
        } finally {
            close(conn, pstmt, null);
        }
    }

    @Override
    public User findUser(String userId) {
        String sql = "select userId, password, name, email from users where userId = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String id = rs.getString(1);
                String password = rs.getString(2);
                String name = rs.getString(3);
                String email = rs.getString(4);
                return new User(id, password, name, email);
            }
            return null;
        } catch (SQLException e) {
            // TODO : 어떤 예외 처리가 필요할까 고민해보기
            logger.error("Failed to findByIndex of DB :" + e);
            return null;
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select userId, password, name, email  from users";
        Connection conn = null;
        Statement stmt  = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString(1);
                String password = rs.getString(2);
                String name = rs.getString(3);
                String email = rs.getString(4);
                users.add(new User(id, password, name, email));
            }
            return users;
        } catch (SQLException e) {
            // TODO : 어떤 예외 처리가 필요할까 고민해보기
            logger.error("Failed to findByIndex of DB :" + e);
            return null;
        } finally {
            close(conn, stmt , rs);
        }
    }

    @Override
    public boolean update(User user) {
        String sql = "update users set name=?, email=? where userId=? AND password=?";
        Connection conn = null;
        PreparedStatement pstmt  = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getUserId());
            pstmt.setString(4, user.getPassword());

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            // TODO : 어떤 예외 처리가 필요할까 고민해보기
            logger.error("Failed to update of DB :" + e);
            return false;
        } finally {
            close(conn, pstmt , rs);
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            logger.error("Failed to connect DB :" + e);
            // TODO : 어떤 예외 처리가 필요할까 고민해보기
            return null;
        }
    }

    private void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            logger.error("Failed to close ResultSet");
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            logger.error("Failed to close Statement");
        }
        try {
            conn.close();
        } catch (SQLException e) {
            logger.error("Failed to close Connection");
        }
    }
}
