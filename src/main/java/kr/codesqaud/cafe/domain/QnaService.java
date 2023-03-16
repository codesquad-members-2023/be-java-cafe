package kr.codesqaud.cafe.domain;

import java.util.List;

import kr.codesqaud.cafe.user.Article;

public interface QnaService {
    void postQna(Article article);
    List<Article> lookupAllArticles();

}
