package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.dto.article.ArticleFormDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private List<Article> articleStore = new ArrayList<>();

    @Override
    public void save(ArticleFormDTO article) {
    }

    @Override
    public Optional<Article> findArticleById(int id) {
        return articleStore.stream()
                .filter(article -> article.getId() == (id))
                .findAny();
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

    @Override
    public List<Reply> findAllReplyByArticleId(int articleId) {
        return null;
    }
}
