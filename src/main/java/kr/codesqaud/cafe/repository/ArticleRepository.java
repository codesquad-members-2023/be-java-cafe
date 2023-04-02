package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Answer;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.answer.AnswerResponseDto;
import kr.codesqaud.cafe.dto.article.ArticleListResponse;
import kr.codesqaud.cafe.dto.article.ArticleResponse;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);
    ArticleResponse findById(long id);
    List<ArticleListResponse> findAll();
    void update(long index, Article newArticle);
    int delete(long id);

    void saveReply(Answer answer);
    List<AnswerResponseDto> findReplyByArticleId(long articleId);
    AnswerResponseDto findReplyById(long id);
    void updateReply(long exAnswerId, String newContents);
    void deleteAReply(long id);
    void deleteAllReply(Long articleId);
}
