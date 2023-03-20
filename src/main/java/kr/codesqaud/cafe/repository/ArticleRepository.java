package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    void saveArticle(Article article);
    Optional<Article> findOneArticleById(int id);
    List<Article> getArticles();
    void deleteArticle(Member member);
    int getSize();
}
