package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void writeArticle(Article article) {
        articleRepository.saveArticle(article);
    }

    public Optional<Article> findOneArticleById(long id) {
        return articleRepository.findOneArticleById(id);
    }

    public List<Article> findArticles() {
        return articleRepository.getArticles();
    }

    public int getTotalNumberOfArticles() {
        return articleRepository.getSize();
    }

    public void updateArticle(Article article) {
        articleRepository.updateArticle(article);
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteArticle(articleId);
    }
}
