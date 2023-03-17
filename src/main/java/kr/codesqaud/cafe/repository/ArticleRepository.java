package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    void saveArticle(Article article);

    Optional<Article> findByIndex(String index);

    List<Article> findAllArticle();
}
