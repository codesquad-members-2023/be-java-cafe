package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryArticleRepository {

    private final List<Article> articles = new ArrayList<>();

    public void save(Article article) {
        article.setId(articles.size() + 1);
        articles.add(article);
    }

    public Article findById(int id) {
        return articles.get(id - 1);
    }

    public List<Article> findAll() {
        return articles;
    }
}
