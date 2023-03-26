package kr.codesqaud.cafe.service;

import java.util.List;

import kr.codesqaud.cafe.model.Article;
import kr.codesqaud.cafe.model.ArticleDto;

public interface QnaService {
    void postQna(Article article);
    List<ArticleDto> lookupAllArticles();

    ArticleDto lookupById(long id);

}
