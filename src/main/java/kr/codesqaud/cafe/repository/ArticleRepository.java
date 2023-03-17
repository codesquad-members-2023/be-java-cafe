package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.article.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ArticleRepository {

    void save(Article article);

    Optional<Article> findArticleByWriter(int id);

    List<Article> findAllArticle();

}
