package kr.codesqaud.cafe.repository;

import java.util.List;

import kr.codesqaud.cafe.exceptions.ArticleInfoException;
import kr.codesqaud.cafe.model.Article;
import kr.codesqaud.cafe.model.ArticleDto;

public interface ArticleRepository {
    public void addArticle(Article article);

    public void modifyArticle(long id, String title, String contents);
    public void deleteArticle(long id);

    public List<ArticleDto> getArticleList();

    public ArticleDto findById(long id) throws ArticleInfoException;
}
