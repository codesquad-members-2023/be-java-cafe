package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Reply;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateReplyRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReplyRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveReply(Reply reply) {
        jdbcTemplate.update("INSERT INTO CAFE_REPLY(WRITER, CONTENTS, ARTICLEID) VALUES (?, ?, ?)"
                , reply.getWriter(), reply.getContents(), reply.getArticleId());
    }

    public boolean updateReply(Reply reply) {
        if (findById(reply.getId()).isPresent()) {
            jdbcTemplate.update("update CAFE_REPLY set CONTENTS=?, time=? where ID=?"
                    , reply.getContents(), LocalDateTime.now(), reply.getId());
            return true;
        }
        return false;
    }

    public boolean deleteReply(long id) {
        if (findById(id).isPresent()) {
            jdbcTemplate.update("DELETE CAFE_REPLY where ID=?", id);
            return true;
        }
        return false;
    }

    public Optional<Reply> findById(long id) {
        List<Reply> result = jdbcTemplate.query("select * from cafe_article where id = ?", replyRowMapper(), id);
        return result.stream().findAny();
    }

    public List<Reply> findAllReply(long articleId) {
        return jdbcTemplate.query("select * from cafe_reply where articleId=?", replyRowMapper(), articleId);
    }

    private RowMapper<Reply> replyRowMapper() {
        return (rs, rowNum) -> {
            Reply reply = new Reply();
            reply.setId(rs.getLong("id"));
            reply.setWriter(rs.getString("writer"));
            reply.setContents(rs.getString("contents"));
            reply.setTime(rs.getTimestamp("time"));
            reply.setArticleId(rs.getLong("articleId"));
            return reply;
        };
    }
}
