package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.dto.article.ArticleFormDTO;

import java.util.List;
import java.util.Optional;


public interface ArticleRepository {

    void save(ArticleFormDTO article);

    Optional<Article> findArticleById(int id);

    List<Article> findAllArticle();

    void updateArticle(String title, String contents, int articleId);

    void deleteArticle(int articleId);

    String findUsernameByArticleUserId(String userId);

    List<Reply> findAllReplyByArticleId(int articleId);
}
