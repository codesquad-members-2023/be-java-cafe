package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.article.ArticleFormDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private List<Article> articleStore = new ArrayList<>();

    @Override
    public void save(ArticleFormDTO article) {
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

    @Override
    public String findUsernameByArticleUserId(String userId) {
        return null;
    }
}
