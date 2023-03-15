package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {
    List<Article> store = new ArrayList<>();

    public void save(Article article) {
        store.add(article);
    }

    public Optional<Article> findById(Long id) {
        return store.stream().filter(e -> e.isIdEquals(id)).findFirst();
    }

    public List<Article> findAll() {
        return new ArrayList<>(Collections.unmodifiableList(store));
    }
}
