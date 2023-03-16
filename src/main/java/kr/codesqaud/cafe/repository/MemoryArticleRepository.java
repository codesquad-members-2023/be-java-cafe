package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private static Map<Long, Article> articleStore = new LinkedHashMap<>();
    private static long sequence = 0L;

    @Override
    public Article saveArticle(Article article) {
        article.setId(++sequence);
        articleStore.put(sequence, article);
        return article;
    }

    @Override
    public List<Article> findAllArticle() {
        return new ArrayList<>(articleStore.values());
    }

    public void clearStore() {
        articleStore.clear();
    }
}
