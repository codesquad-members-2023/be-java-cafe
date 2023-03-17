package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.model.Article;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final List<Article> articleList;

    public ArticleService(List<Article> articleList) {
        this.articleList = articleList;
    }

    public void creatArticle(Article article) {
        article.giveId(articleList.size() + 1);
        articleList.add(article);
    }

    public List<Article> findAllArticle() {
        return articleList;
    }

    public Article findArticleContentById(int articleId) {
        return articleList.stream()
                .filter(s -> s.getId() == articleId)
                .collect(Collectors.toList())
                .get(0);
    }
}
