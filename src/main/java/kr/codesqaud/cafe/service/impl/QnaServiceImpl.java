package kr.codesqaud.cafe.service.impl;

import java.util.List;

import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.service.QnaService;
import kr.codesqaud.cafe.model.Article;

public class QnaServiceImpl implements QnaService {

    private ArticleRepository articleRepository;

    public QnaServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void postQna(Article article) {
        articleRepository.addArticle(article);
    }

    @Override
    public List<Article> lookupAllArticles() {
        return articleRepository.getArticleList();
    }

    @Override
    public Article lookupById(long id) {
        return articleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("[ERROR]"));
    }
}
