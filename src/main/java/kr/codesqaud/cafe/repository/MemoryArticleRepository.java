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
        article.setId((long)articles.size() + 1);
        articles.add(article);
    }

    @Override
    public void update(String title, String content, String articleId) {

    }

    @Override
    public List<Article> findAll() {
        return articles;
    }

    @Override
    public Article findById(long index) {
        return articles.get((int)index - 1);
    }
}
