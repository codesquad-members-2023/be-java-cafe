package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private List<Article> articleStore = new ArrayList<>();

    @Override
    public void save(Article article) {
        article.setId(articleStore.size());
        articleStore.add(article);
    }

    @Override
    public Article findArticleById(int id) {
        return articleStore.stream()
                .filter(article -> article.getId() == (id))
                .findAny()
                .orElseThrow();
    }

    @Override
    public List<Article> findAllArticle() {
        return articleStore;
    }

    @Override
    public void updateArticle(String title, String contents, int articleId) {

    }

    @Override
    public void deleteArticle(int articleId) {

    }
}
