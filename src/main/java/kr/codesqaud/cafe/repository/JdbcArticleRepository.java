package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class JdbcArticleRepository implements ArticleRepository {
    private final Queue<Article> store = new ConcurrentLinkedQueue<>();

    @Override
    public void save(Article article) {
        store.add(article);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return store.stream()
                .filter(e -> e.equals(id))
                .findAny();
    }

    @Override
    public List<Article> findAll() {
        return List.copyOf(store);
    }
}
