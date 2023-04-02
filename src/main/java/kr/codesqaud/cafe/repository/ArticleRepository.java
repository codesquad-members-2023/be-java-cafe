package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.article.ArticleListResponse;
import kr.codesqaud.cafe.dto.article.ArticleResponse;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);
    ArticleResponse findById(long id);
    List<ArticleListResponse> findAll();
    void update(long index, Article newArticle);

    void delete(long id);
}
