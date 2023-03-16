package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.article.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryArticleRepository implements ArticleRepository{

    private List<Article> articleStore = new ArrayList<>();

    @Override
    public void save(Article article) {
        articleStore.add(article);
    }

    @Override
    public Optional<Article> findArticleByWriter(String writer) {
        return articleStore.stream()
                .filter(article -> article.getWriter().equals(writer))
                .findFirst();
    }

    @Override
    public List<Article> findAllArticle() {
        return articleStore;
    }
}
