package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MemoryArticleRepository implements ArticleRepository {
    private static Map<Long, Article> articleStore = new LinkedHashMap<>();
    private static long sequence = 0L;

    @Override
    public Article save(Article article) {
        articleStore.put(++sequence, article);
        return article;
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articleStore.values());
    }

    public void clearStore() {
        articleStore.clear();
    }
}
