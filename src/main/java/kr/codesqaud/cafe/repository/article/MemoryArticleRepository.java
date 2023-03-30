package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private Logger log = LoggerFactory.getLogger(getClass());
    private static final Map<Long, Article> store = new ConcurrentHashMap<>();

    public void save(Article article) {
        long key = article.getId();
        store.put(key, article);
    }

    public List<Article> findAllArticles() {
        return new ArrayList<>(store.values());
    }

    public Article findByArticleId(long id) {
        Article article = store.get(id);
        return article;
    }
}
