package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.basic.Article;

import java.util.List;

public interface ArticleRepository {

    public void save(Article article) ;

    public Article findByIndex(int index);

    public List<Article> findAll() ;
}
