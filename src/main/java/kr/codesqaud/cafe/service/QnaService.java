package kr.codesqaud.cafe.service;

import java.util.List;

import kr.codesqaud.cafe.model.Article;

public interface QnaService {
    void postQna(Article article);
    List<Article> lookupAllArticles();

    Article lookupById(long id);

}
