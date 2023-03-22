package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;

import java.util.List;


public interface ArticleRepository {

    void save(Article article);

    Article findArticleById(int id);

    List<Article> findAllArticle();

    void updateArticle(String title, String contents, int articleId);

    void deleteArticle(int articleId);
}
