package kr.codesqaud.cafe.cafeservice.repository.reply;

import kr.codesqaud.cafe.cafeservice.domain.Reply;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository {

    void save(Reply reply);

    Optional<Reply> findById(Long userId);

    List<Reply> findAll(Long id);

    void update();

    void delete(Long id);

    Optional<Reply> findByWithArticle(Long articleId, Long id);
}
