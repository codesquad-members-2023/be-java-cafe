package kr.codesqaud.cafe.service;

import java.util.List;
import kr.codesqaud.cafe.model.Article;
import kr.codesqaud.cafe.repository.ArticleDao;
import kr.codesqaud.cafe.repository.ArticleDto;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleDao articleDao;
    private static int articleId = 2;

    public ArticleService(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public void creatArticle(Article article) {
        articleDao.addArticle(article);
    }

    public List<ArticleDto> findAllArticle() {
        return articleDao.findAllArticle();
    }

    public ArticleDto findArticleContentById(int articleId) {
        final int FIRST_ARTICLE = 0;
        return articleDao.findArticleContentById(articleId).get(FIRST_ARTICLE);
    }

    public static int getArticleSize() {
        return ++articleId;
    }
}
