package kr.codesqaud.cafe.repository.reply;


import kr.codesqaud.cafe.domain.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {

    void save(Reply reply) ;

    Optional<Reply> findOne(Long id);

    List<Reply> findAll(Long id);

    void update(Reply reply);

    void delete(Long id);
}
