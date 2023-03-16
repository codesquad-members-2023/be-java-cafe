package kr.codesqaud.cafe.domain;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Article> lookupAllArticles() {
        return articleRepository.getArticleList();
    }

    @Override
    public Optional<Article> lookupById(long id) {
        return articleRepository.findById(id);
    }
}
