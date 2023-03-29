package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.JdbcTemplateArticleRepository;

import java.util.List;
import java.util.Optional;

public class ArticleService {

    private final JdbcTemplateArticleRepository articleRepository;

    public ArticleService(JdbcTemplateArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * 게시글 작성 & 수정
     */
    public boolean write(Article article) {
        return articleRepository.saveArticle(article);
    }

    public boolean update(Article article) {
        return articleRepository.updateArticle(article);
    }

    /**
     * 게시글 조회
     */
    public List<Article> findAllArticle() {
        return articleRepository.findAllArticle();
    }

    public Optional<Article> findByArticleId(long id) {
        return articleRepository.findById(id);
    }

    /**
     * 게시글 검증
     */
    public Article articleCheck(long id, User sessionUser) {
        return articleRepository.findById(id)
                .filter(article -> article.getWriter().equals(sessionUser.getName()))
                .orElseThrow(null);
    }

    public boolean sessionCheck(long id, User sessionUser) {
        Article checkId = articleCheck(id, sessionUser);
        return checkId != null && checkId.getWriter().equals(sessionUser.getName());
    }

    /**
     * 게시글 삭제
     */
    public boolean delete(long id) {
        return articleRepository.deleteArticle(id);
    }
}
