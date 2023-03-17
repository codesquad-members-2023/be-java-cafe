package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.model.Article;

public interface QnaService {
    void postQna(Article article);
    List<Article> lookupAllArticles();

    Optional<Article> lookupById(long id);

}
