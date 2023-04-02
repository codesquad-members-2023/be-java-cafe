package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.article.Reply;
import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.dto.ReplyResponse;
import kr.codesqaud.cafe.dto.ArticleListResponse;
import kr.codesqaud.cafe.dto.ArticleResponse;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);
    ArticleResponse findById(long id);
    List<ArticleListResponse> findAll();
    void update(long index, Article newArticle);
    int delete(long id);

    long saveReply(Reply answer);
    List<ReplyResponse> findReplyByArticleId(long articleId);
    ReplyResponse findReplyById(long id);
    void updateReply(long exAnswerId, String newContents);
    void deleteAReply(long id);
    void deleteAllReply(Long articleId);
}
