package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {

    void save(Article article);

    Article findById(int id);

    List<Article> findAll();

    void delete(int id);
}
