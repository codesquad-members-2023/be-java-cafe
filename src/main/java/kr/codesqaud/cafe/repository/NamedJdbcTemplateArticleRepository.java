package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.dto.ArticleWithWriterDto;
import kr.codesqaud.cafe.dto.ArticleWithoutContentsDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

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

	public List<ArticleWithoutContentsDto> findAllArticlesWithoutContents() {
		String sql = "select a.article_number, a.article_title, a.article_writtentime, a.member_number, m.member_id as writer, " +
			"from article a join member m on a.member_number=m.member_number";
		return template.query(sql, articleWithoutContentsDtoRowMapper());
	}

	public ArticleWithWriterDto findByArticleSequence(Long articleSequence) {
		String sql = "select a.article_number, a.article_title, a.article_contents, a.article_writtentime, a.member_number," +
			"m.member_id from article a inner join member m on a.member_number = m.member_number where a.article_number=:articleSequence";
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource("articleSequence", articleSequence);
		return template.queryForObject(sql, sqlParameterSource, articleWithWriterDtoRowMapper());
	}

	public void update(ArticleWithWriterDto updateParam) {
		String sql = "update article set article_title=:title, article_contents=:contents where article_number=:articleSequence";
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(updateParam);
		template.update(sql, sqlParameterSource);
	}

	public void delete(ArticleWithWriterDto article) {
		String sql = "delete from article where article_number=:articleSequence";
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(article);
		template.update(sql, sqlParameterSource);
	}

	private RowMapper<Article> articleRowMapper() {
		return (rs, rowNum) -> {
			Article article = new Article();
			article.setArticleSequence(rs.getLong("article_number"));
			article.setTitle(rs.getString("article_title"));
			article.setContents(rs.getString("article_contents"));
			article.setWrittenTime(rs.getTimestamp("article_writtentime").toLocalDateTime());
			return article;
		};
	}

	private RowMapper<ArticleWithWriterDto> articleWithWriterDtoRowMapper() {
		return (rs, rowNum) -> {
			ArticleWithWriterDto articleWithWriterDto = new ArticleWithWriterDto();
			articleWithWriterDto.setArticleSequence(rs.getLong("article_number"));
			articleWithWriterDto.setTitle(rs.getString("article_title"));
			articleWithWriterDto.setContents(rs.getString("article_contents"));
			articleWithWriterDto.setWrittenTime(rs.getTimestamp("article_writtentime").toLocalDateTime());
			articleWithWriterDto.setUserSequence(rs.getLong("member_number"));
			articleWithWriterDto.setWriter(rs.getString("member_id"));
			return articleWithWriterDto;
		};
	}

	private RowMapper<ArticleWithoutContentsDto> articleWithoutContentsDtoRowMapper() {
		return (rs, rowNum) -> {
			ArticleWithoutContentsDto articleWithoutContentsDto = new ArticleWithoutContentsDto();
			articleWithoutContentsDto.setArticleSequence(rs.getLong("article_number"));
			articleWithoutContentsDto.setTitle(rs.getString("article_title"));
			articleWithoutContentsDto.setWrittenTime(rs.getTimestamp("article_writtentime").toLocalDateTime());
			articleWithoutContentsDto.setUserSequence(rs.getLong("member_number"));
			articleWithoutContentsDto.setWriter(rs.getString("member_id"));
			return articleWithoutContentsDto;
		};
	}
}
