package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;
import java.util.Optional;


public interface ArticleRepository {

    void save(Article article);

    Article findArticleByWriter(int id);

    List<Article> findAllArticle();

    void delete(int id);

}
