package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.dto.ArticleForm;
import kr.codesqaud.cafe.domain.dto.ArticleWithWriter;
import kr.codesqaud.cafe.domain.dto.SimpleArticleWithWriter;
import kr.codesqaud.cafe.utils.Paging;

import java.util.List;

public interface ArticleRepository {

    void save(Article article);

    ArticleWithWriter findById(int id);

    List<SimpleArticleWithWriter> findAll(Paging paging);

    int count();

    void delete(int id);

    void update(int id, ArticleForm articleForm);
}
