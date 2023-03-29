package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {

    void save(Article article);

    List<Article> findAllArticles();

    Article findByArticleId(long id);

    void deleteAriticle(long id);
}
