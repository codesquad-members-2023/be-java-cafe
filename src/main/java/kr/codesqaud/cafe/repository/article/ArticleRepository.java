package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {

    void save(Article article);

    List<Article> findAllArticles();

    Article findByArticleId(long id);

    void updateArticle(long articleId, String title, String contents);

    void deleteArticle(long id);
}
