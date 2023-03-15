package kr.codesqaud.cafe.domain;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.user.Article;

public class QnaServiceImpl implements QnaService {

    private ArticleRepository articleRepository;

    public QnaServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void postQna(Article article) {
        articleRepository.addArticle(article);
    }
}
