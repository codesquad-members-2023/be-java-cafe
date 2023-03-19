package kr.codesqaud.cafe.repository.h2Repository;


import kr.codesqaud.cafe.basic.Article;
import kr.codesqaud.cafe.config.repositoryConfig.ConnectionConfig;
import kr.codesqaud.cafe.repository.ArticleRepository;
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
public class H2ArticleRepository implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger("article database");

    @Autowired
    public H2ArticleRepository()  {}

    public void add(Article article) {
        String sql = "insert into articles(articleId, writer, title, contents) values (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            int articleId = getArticleSize() + 1;

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, articleId);
            pstmt.setString(2, article.getWriter());
            pstmt.setString(3, article.getTitle());
            pstmt.setString(4, article.getContents());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            // TODO : 어떤 예외 처리가 필요할까 고민해보기
            logger.error("Failed to add of DB :" + e);
        } finally {
            close(conn, pstmt, null);
        }
    }

    public Article findByIndex(int index) {
        String sql = "select articleId, writer, title, contents from articles where articleId = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, index);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int articleId = rs.getInt(1);
                String writer = rs.getString(2);
                String title = rs.getString(3);
                String contents = rs.getString(4);
                return new Article(articleId, writer, title, contents);
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

    public List<Article> findAll() {
        String sql = "select articleId, writer, title, contents from articles";
        Connection conn = null;
        Statement stmt  = null;
        ResultSet rs = null;
        List<Article> articles = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int articleId = rs.getInt(1);
                String writer = rs.getString(2);
                String title = rs.getString(3);
                String contents = rs.getString(4);
                articles.add(new Article(articleId, writer, title, contents));
            }
            return articles;
        } catch (SQLException e) {
            // TODO : 어떤 예외 처리가 필요할까 고민해보기
            logger.error("Failed to findByIndex of DB :" + e);
            return null;
        } finally {
            close(conn, stmt , rs);
        }
    }

    private int getArticleSize() throws SQLException {
        String sql = "select count(*) from articles";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            // TODO : 어떤 예외 처리가 필요할까 고민해보기
            logger.error("Failed to get size of DB :" + e);
            throw e;

        } finally {
            close(conn, stmt, null);
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

    // TODO : 왜 ResultSet과 Statement를 close 해주는 것이 좋은지 알아보기
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
