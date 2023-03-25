package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.dto.ArticleWithWriter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final List<Article> articles = new ArrayList<>();

    @Override
    public void save(Article article) {
        article.setId(articles.size() + 1);
        articles.add(article);
    }

    @Override
    public ArticleWithWriter findById(int id) {
        if (!articles.stream().anyMatch(article -> article.getId() == id)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 게시글입니다!");
        }
        Article findArticle = articles.get(id - 1);
        return new ArticleWithWriter(findArticle.getId(), findArticle.getTitle(), findArticle.getContents(), findArticle.getUserId(), "user_id");
    }

    @Override
    public List<ArticleWithWriter> findAll() {
        ArrayList<Article> articles = new ArrayList<>(this.articles);
        Collections.reverse(articles);

        return articles
                .stream()
                .map(a -> new ArticleWithWriter(a.getId(), a.getTitle(), a.getContents(), a.getUserId(), "userId"))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        checkArticleExist(id, "삭제");

        articles.remove(id - 1);
    }

    @Override
    public void update(int id, Article updateArticle) {
        checkArticleExist(id, "수정");

        Article originalArticle = articles.get(id - 1);

        originalArticle.setTitle(updateArticle.getTitle());
        originalArticle.setContents(updateArticle.getContents());
    }

    private void checkArticleExist(int id, String action) {
        if (articles.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 게시글이 아무것도 없어 " + action + "할 수 없습니다.");
        }

        if (!articles.stream().anyMatch(article -> article.getId() == id)) {
            throw new IllegalArgumentException("[ERROR] 해당하는 게시글이 없어 " + action + "할 수 없습니다.");
        }
    }
}
