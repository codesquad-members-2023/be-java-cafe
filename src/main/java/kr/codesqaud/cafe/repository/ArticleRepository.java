package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);
    Article findById(Long id);
    List<Article> findAll();
    void update(Article exArticle, Article newArticle);
    void delete(long id);
}
