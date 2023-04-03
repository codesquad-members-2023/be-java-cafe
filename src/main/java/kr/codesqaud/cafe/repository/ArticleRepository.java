package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.dto.ArticleForm;
import kr.codesqaud.cafe.domain.dto.ArticleWithWriter;
import kr.codesqaud.cafe.domain.dto.SimpleArticleWithWriter;

import java.util.List;

public interface ArticleRepository {

    void save(Article article);

    ArticleWithWriter findById(int id);

    List<SimpleArticleWithWriter> findAll();

    void delete(int id);

    void update(int id, ArticleForm articleForm);
}
