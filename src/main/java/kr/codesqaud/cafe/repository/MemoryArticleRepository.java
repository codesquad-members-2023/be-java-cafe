package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private final List<Article> articleStore = new ArrayList<>();

    @Override
    public boolean saveArticle(Article article) {
        article.setId(articleStore.size() + 1);
        articleStore.add(article);

        return true;
    }

    @Override
    public Optional<Article> findById(long id) {
        return articleStore.stream()
                .filter(user -> user.isIndexEquals(id))
                .findFirst();
    }

    @Override
    public List<Article> findAllArticle() {
        return new ArrayList<>(Collections.unmodifiableList(articleStore));
    }

    public void clearStore() {
        articleStore.clear();
    }
}
