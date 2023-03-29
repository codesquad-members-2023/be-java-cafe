package kr.codesqaud.cafe.repository;

import java.util.List;

import kr.codesqaud.cafe.exceptions.ArticleInfoException;
import kr.codesqaud.cafe.model.Article;
import kr.codesqaud.cafe.model.ArticleDto;
import kr.codesqaud.cafe.model.ArticleReplyCountsDto;

public interface ArticleRepository {
    public void addArticle(Article article);

    public void modifyArticle(long id, String title, String contents);
    public void deleteArticle(long id);

    public List<ArticleReplyCountsDto> getArticleList();

    public ArticleDto findById(long id) throws ArticleInfoException;
}
