package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryArticleRepository implements ArticleRepository {
    private List<Article> articlesRepository;
    private final int ID_PLUS = 1;

    public MemoryArticleRepository() {
        articlesRepository = new ArrayList<>();
    }

    @Override
    public void saveArticle(Article article) {
        article.setArticleId(articlesRepository.size() + ID_PLUS);
        articlesRepository.add(article);
    }

    @Override
    public Optional<Article> findOneArticleById(int id) {
        return articlesRepository.stream()
                .filter(article -> article.getId() == id)
                .findAny();
    }

    @Override
    public List<Article> getArticles() {
        return articlesRepository;
    }

    @Override
    public int getSize() {
        return articlesRepository.size();
    }

    @Override
    public void deleteArticle(Member member) {

    }

    public void clearRepository() {
        articlesRepository.clear();
    }
}
