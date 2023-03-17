package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryArticleRepository implements ArticleRepository {
    private List<Article> articlesRepository;

    public MemoryArticleRepository() {
        articlesRepository = new ArrayList<>();
    }

    @Override
    public void saveArticle(Article article) {
        articlesRepository.add(article);
    }

    @Override
    public Optional<Article> findOneArticleByTitle(String title) {
        return articlesRepository.stream()
                .filter(article -> article.getTitle().equals(title))
                .findAny();
    }

    @Override
    public List<Article> findArticles() {
        return articlesRepository;
    }

    @Override
    public void deleteArticle(Member member) {

    }
}
