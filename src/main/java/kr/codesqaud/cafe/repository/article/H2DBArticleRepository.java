package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.DBConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
@Primary
public class H2DBArticleRepository implements ArticleRepository {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void save(Article article) {
        String sql = "insert into article (id, writer, title, contents, timestamp) values(?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            log.info("save test");
            con = connect();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, getDBSize() + 1);
            pstmt.setString(2, article.getWriter());
            pstmt.setString(3, article.getTitle());
            pstmt.setString(4, article.getContents());
            pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();


        } catch (SQLException e) {
            log.info("article save error");
            throw new RuntimeException(e);
        } finally {
            close(con, pstmt, null);
        }
    }

    @Override
    public Article findArticleByWriter(int id) {
        String sql = "select * from article where id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = connect();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String writer = rs.getString("writer");
                String title = rs.getString("title");
                String contents = rs.getString("contents");
                Timestamp timestamp = rs.getTimestamp("timestamp");
                Article article = new Article(writer, title, contents, timestamp);
                article.setId(id);
                return article;
            } else {
                throw new NoSuchElementException("article not found airticleId = " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con, pstmt, rs);
        }
    }

    @Override
    public List<Article> findAllArticle() {
        List<Article> articles = new ArrayList<>();
        String sql = "select * from article";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = connect();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String writer = rs.getString("writer");
                String title = rs.getString("title");
                String contents = rs.getString("contents");
                Timestamp timestamp = rs.getTimestamp("timestamp");
                Article article = new Article(writer, title, contents, timestamp);
                article.setId(id);
                articles.add(article);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con, pstmt, rs);
        }


        return articles;
    }

    // 테스트용 코드
    @Override
    public void delete(int userId) {
        String sql = "delete from article where id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = connect();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con, pstmt, null);
        }
    }


    private int getDBSize() {
        String sql = "select count(id) as row_count from article";
        int count = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = connect();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("row_count");
            }

            log.info(String.valueOf(count));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con, pstmt, rs);
        }
        return count;
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
