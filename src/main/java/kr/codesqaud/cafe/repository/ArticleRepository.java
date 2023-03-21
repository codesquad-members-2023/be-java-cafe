package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.model.Article;

public interface ArticleRepository {
    public void addArticle(Article article);

    public List<Article> getArticleList();

    public Optional<Article> findById(long id);
}
