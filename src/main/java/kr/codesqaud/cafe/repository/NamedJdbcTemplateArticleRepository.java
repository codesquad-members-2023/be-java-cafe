package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.dto.ArticleWithWriterDto;
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

	public void write(Article article) {
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(article);
		template.update("insert into article(article_title, article_contents, member_number) values (:title, :contents, :userSequence)", sqlParameterSource);
	}

//	public List<Article> showAllArticles() {
//		String sql = "select article_number, article_title, article_contents, article_writtentime from article";
//		return template.query(sql, articleRowMapper());
//	}

	public List<ArticleWithWriterDto> showAllArticles() {
		String sql = "select a.article_number, a.article_title, a.article_contents, a.article_writtentime, a.member_number, m.member_id as writer, " +
			"from article a join member m on a.member_number=m.member_number";
		return template.query(sql, articleWithWriterDtoRowMapper());
	}

//	public Article findByArticleSequence(Long articleSequence) {
//		String sql = "select article_number, article_title, article_contents, article_writtentime from article where article_number = :articleSequence";
//		SqlParameterSource sqlParameterSource = new MapSqlParameterSource("articleSequence", articleSequence);
//		return template.queryForObject(sql, sqlParameterSource, articleRowMapper());
//	}

	public ArticleWithWriterDto findByArticleSequence(Long articleSequence) {
		String sql = "select a.article_number, a.article_title, a.article_contents, a.article_writtentime, a.member_number, " +
			"(select member_id from member m where m.member_number=a.member_number) as writer from article a where a.article_number=:articleSequence";
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource("articleSequence", articleSequence);
		return template.queryForObject(sql, sqlParameterSource, articleWithWriterDtoRowMapper());
	}

	private RowMapper<Article> articleRowMapper() {
		return new RowMapper<Article>() {
			@Override
			public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
				Article article = new Article();
				article.setArticleSequence(rs.getLong("article_number"));
				article.setTitle(rs.getString("article_title"));
				article.setContents(rs.getString("article_contents"));
				article.setWrittenTime(rs.getTimestamp("article_writtentime").toLocalDateTime());
				return article;
			}
		};
	}

	private RowMapper<ArticleWithWriterDto> articleWithWriterDtoRowMapper() {
		return new RowMapper<ArticleWithWriterDto>() {
			@Override
			public ArticleWithWriterDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				ArticleWithWriterDto articleWithWriterDto = new ArticleWithWriterDto();
				articleWithWriterDto.setArticleSequence(rs.getLong("article_number"));
				articleWithWriterDto.setTitle(rs.getString("article_title"));
				articleWithWriterDto.setContents(rs.getString("article_contents"));
				articleWithWriterDto.setWrittenTime(rs.getTimestamp("article_writtentime").toLocalDateTime());
				articleWithWriterDto.setUserSequence(rs.getLong("member_number"));
				articleWithWriterDto.setWriter(rs.getString("writer"));
				return articleWithWriterDto;
			}
		};
	}
}
