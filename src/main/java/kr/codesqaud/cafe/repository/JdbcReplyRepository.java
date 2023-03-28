package kr.codesqaud.cafe.repository;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.codesqaud.cafe.model.ArticleDto;
import kr.codesqaud.cafe.model.Reply;
import kr.codesqaud.cafe.model.ReplyDto;

public class JdbcReplyRepository implements ReplyRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcReplyRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addReply(Reply reply) {
        jdbcTemplate.update(
                "insert into replies(writer, contents, creationTime, articleId) values(?, ?, ?, ?)",
                reply.getUser().getId(),
                reply.getContents(), reply.getCreationTime(), reply.getArticleId());
    }

    public List<ReplyDto> getReplyList() {
        return jdbcTemplate.query(
                "select id, writer, contents, articleId, creationTime from replies order by id",
                replyRowMapper());
    }

    private RowMapper<ReplyDto> replyRowMapper() {
        return (rs, rowNum) ->
                new ReplyDto(rs.getLong("id"), rs.getString("writer"),
                        rs.getString("contents"), rs.getTimestamp("creationTime").toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), rs.getLong("articleId"));

    }

}
