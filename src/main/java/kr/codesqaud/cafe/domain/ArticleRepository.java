package kr.codesqaud.cafe.domain;

import java.util.List;

import kr.codesqaud.cafe.user.Article;

public interface ArticleRepository {
    public void addArticle(Article article);

    public List<Article> getArticleList();
}
