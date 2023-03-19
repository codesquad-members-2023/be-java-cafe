package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Repository
public class ArticleRepository {
    private final Queue<Article> store = new ConcurrentLinkedQueue<>();

    public void save(Article article) {
        store.add(article);
    }

    public Optional<Article> findById(Long id) {
        return store.stream()
                .filter(e -> e.equals(id))
                .findAny();
    }

    public List<Article> findAll() {
        return List.copyOf(store);
    }
}
