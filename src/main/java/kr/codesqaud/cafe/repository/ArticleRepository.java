package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {
    Article saveArticle(Article article);

    List<Article> findAllArticle();
}
