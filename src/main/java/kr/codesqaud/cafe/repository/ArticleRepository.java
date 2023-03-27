package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.connection.DBConnectionUtility;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class ArticleRepository {

	public Article write(Article article) throws SQLException {
		String sql = "insert into article(article_writer, article_title, article_contents) values (?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getWriter());
			pstmt.setString(2, article.getTitle());
			pstmt.setString(3, article.getContents());
			pstmt.executeUpdate();
			return article;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(con, pstmt, null);
		}
	}

	public List<Article> showAllArticles() throws SQLException {
		String sql = "select * from article";

		List<Article> allArticles = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				Article article = new Article();
				article.setArticleSequence(resultSet.getLong("article_number"));
				article.setWriter(resultSet.getString("article_writer"));
				article.setTitle(resultSet.getString("article_title"));
				article.setContents(resultSet.getString("article_contents"));
				article.setWrittenTime(resultSet.getTimestamp("article_writtentime").toLocalDateTime());
				allArticles.add(article);
			}
			return allArticles;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(con, pstmt, resultSet);
		}
	}

	public Article findByArticleSequence(Long articleSequence) throws SQLException {
		String sql = "select * from article where article_number = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, articleSequence);

			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				Article article = new Article();
				article.setWriter(resultSet.getString("article_writer"));
				article.setTitle(resultSet.getString("article_title"));
				article.setContents(resultSet.getString("article_contents"));
				article.setWrittenTime(resultSet.getTimestamp("article_writtentime").toLocalDateTime());
				return article;
			} else {
				throw new NoSuchElementException("article not found articleSequence=" + articleSequence);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			close(con, pstmt, resultSet);
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
