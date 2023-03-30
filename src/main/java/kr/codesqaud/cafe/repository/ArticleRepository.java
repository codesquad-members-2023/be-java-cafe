package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);

    void update(String title, String content, String articleId);

    void delete(String id);

    List<Article> findAll();

    Article findById(long index);
}
