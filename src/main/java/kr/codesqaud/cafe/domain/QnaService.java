package kr.codesqaud.cafe.domain;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.user.Article;

public interface QnaService {
    void postQna(Article article);
    List<Article> lookupAllArticles();

    Optional<Article> lookupById(long id);

}
