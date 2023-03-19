package kr.codesqaud.cafe.domain.article;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepository {

    private static final List<Article> articleRepository = new ArrayList<Article>();

    private static long sequence = 0L;

    public Article write(Article article) {
        article.setArticleSequence(++sequence);
        articleRepository.add(article);
        return article;
    }

    public List<Article> showAllArticles() {
        List<Article> allArticles = new ArrayList<>();
        for (int i = 0; i < articleRepository.size(); i++) {
            allArticles.add(articleRepository.get(i));
        }
        return allArticles;
    }

    public Article findByArticleSequence(Long articleSequence) {
        return articleRepository.stream().filter(user -> user.getArticleSequence().equals(articleSequence)).findFirst().orElseThrow();
    }
}
