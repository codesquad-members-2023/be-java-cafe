package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//@Repository
public class MemoryArticleRepository implements ArticleRepository{
    private static final List<Article> articles = new ArrayList<>();

    @Override
    public void save(Article article) {
        article.setIndex((long)articles.size() + 1);
        articles.add(article);
    }

    @Override
    public List<Article> findAll() {
        return articles;
    }

    @Override
    public Article findByIndex(int index) {
        return articles.get(index - 1);
    }
}
