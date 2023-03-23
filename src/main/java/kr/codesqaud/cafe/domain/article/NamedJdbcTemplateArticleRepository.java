package kr.codesqaud.cafe.domain.article;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class NamedJdbcTemplateArticleRepository {

	private final NamedParameterJdbcTemplate template;

	public NamedJdbcTemplateArticleRepository(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	public Article write(Article article) {
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(article);
		template.update("insert into article(article_writer, article_title, article_contents) values (:writer, :title, :contents)", sqlParameterSource);
		return article;
	}

	public List<Article> showAllArticles() {
		String sql = "select article_number, article_writer, article_title, article_contents, article_writtentime from article";
		return template.query(sql, articleRowMapper());
	}

	public Article findByArticleSequence(Long articleSequence) {
		String sql = "select article_number, article_writer, article_title, article_contents, article_writtentime from article where article_number = :articleSequence";
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource("articleSequence", articleSequence);
		return template.queryForObject(sql, sqlParameterSource, articleRowMapper());
	}

	private RowMapper<Article> articleRowMapper() {
		return new RowMapper<Article>() {
			@Override
			public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
				Article article = new Article();
				article.setArticleSequence(rs.getLong("article_number"));
				article.setWriter(rs.getString("article_writer"));
				article.setTitle(rs.getString("article_title"));
				article.setContents(rs.getString("article_contents"));
				article.setWrittenTime(rs.getTimestamp("article_writtentime").toLocalDateTime());
				return article;
			}
		};
	}
}
