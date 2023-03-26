package kr.codesqaud.cafe.repository.memoryRepository;


import kr.codesqaud.cafe.basic.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final List<Article> articleRepository;

    @Autowired
    public MemoryArticleRepository() {
        this.articleRepository = new ArrayList<>();
    }

    public int save(Article article) {
        int articleId = articleRepository.size() + 1;
        article.setArticleId(articleId);
        articleRepository.add(article);
        return 0;
    }

    public Article findByArticleId(int index) {
        return new Article(articleRepository.get(index));
    }

    public List<Article> findAll() {
        return new ArrayList<>(articleRepository);
    }

    @Override
    public int update(int articleId, String Title, String content) {
        return 0;
    }

    @Override
    public int delete(int articleId) {
        return 0;
    }
}
