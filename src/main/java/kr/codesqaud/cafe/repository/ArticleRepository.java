package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.basic.Article;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public interface ArticleRepository {

    public void add(Article article) ;

    public Article findByIndex(int index);

    public List<Article> findAll() ;
}
