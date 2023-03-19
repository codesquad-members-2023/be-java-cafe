package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Long save(Article article);

    Optional<Article> findById(Long id);

    List<Article> findAll();

    void update(Article article);

    void delete(Long id);
}

