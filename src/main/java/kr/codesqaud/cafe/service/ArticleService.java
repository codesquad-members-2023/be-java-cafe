package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;

import java.util.Optional;

public class ArticleService {
    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void writeArticle(Article article) {
        articleRepository.saveArticle(article);
    }

    public Optional<Article> findOneArticleByTitle(String title) {
        return articleRepository.findOneArticleByTitle(title);
    }
}
