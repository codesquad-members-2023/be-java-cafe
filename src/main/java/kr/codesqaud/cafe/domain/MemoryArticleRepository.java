package kr.codesqaud.cafe.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.user.Article;

public class MemoryArticleRepository implements ArticleRepository {
    private List<Article> articleList = new ArrayList<>();

    public void addArticle(Article article) {
        final long index = articleList.size() + 1;
        article.setId(index);
        articleList.add(article);
    }

    @Override
    public List<Article> getArticleList() {
        return articleList;
    }

    @Override
    public Optional<Article> findById(long id) {
        return Optional.ofNullable(articleList.get((int)id - 1));
    }
}
