package kr.codesqaud.cafe.repository;


import kr.codesqaud.cafe.basic.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryArticleRepository {

    private final List<Article> articleRepository;

    public MemoryArticleRepository() {
        this.articleRepository = new ArrayList<>();
    }

    public void add(Article article) {
        articleRepository.add(article);
    }

    public Article findByIndex(int index) {
        return new Article(articleRepository.get(index));
    }

    public List<Article> findAll() {
        return new ArrayList<>(articleRepository);
    }

    public void clear() {articleRepository.clear();}

    public int getSize() {
        return articleRepository.size();
    }

}
