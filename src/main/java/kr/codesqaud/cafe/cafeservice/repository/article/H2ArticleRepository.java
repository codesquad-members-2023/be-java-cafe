package kr.codesqaud.cafe.cafeservice.repository.article;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class H2ArticleRepository {

    private static Logger log = LoggerFactory.getLogger(H2ArticleRepository.class);
    private static String URL = "jdbc:h2:tcp://localhost/~/cafe";
    private static String USERNAME = "sa";
    private static String PASSWORD = "";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("connection={}", connection);
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public Article save(Article article) throws SQLException {
        String sql = "insert into article (writer,title,content) values(?,?,?)";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, article.getWriter());
            pstmt.setString(2, article.getTitle());
            pstmt.setString(3, article.getContent());
            pstmt.executeUpdate();
            return article;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    // TODO article 저장, 회원가입에 저장된 회원의 iD와 article의 글쓴이와 일치하는지 확인.
    public Optional<Article> findById(Long id) throws SQLException {
        String sql = "select * from ARTICLE where id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Article article = new Article();
                article.setWriter(rs.getString("writer"));
                article.setTitle(rs.getString("title"));
                article.setContent(rs.getString("content"));
                return Optional.of(article);
            } else {
                throw new NoSuchElementException("member not found memberId=" +
                        id);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }

    public List<Article> findAll() throws SQLException {
        String sql = "select * from article";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String writer = rs.getString("writer");
                String title = rs.getString("title");
                String content = rs.getString("content");
                System.out.println(id + ", " + writer + ", " + title + ", " + content);

            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
        return null;
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
}
