package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepository {
    private static final List<Article> articles = new ArrayList<>();

    public void save(Article article) {
        articles.add(article);
        System.out.println(article);
    }
}
