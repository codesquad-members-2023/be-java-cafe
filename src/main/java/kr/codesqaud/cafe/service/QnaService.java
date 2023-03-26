package kr.codesqaud.cafe.service;

import java.util.List;

import kr.codesqaud.cafe.model.Article;
import kr.codesqaud.cafe.model.ArticleDto;

public interface QnaService {
    void postQna(Article article);
    void modifyQna(long id, String title, String contents);
    void deleteQna(long id);

    List<ArticleDto> lookupAllArticles();

    ArticleDto lookupById(long id);


}
