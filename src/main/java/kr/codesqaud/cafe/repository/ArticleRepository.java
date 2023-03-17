package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {

    private static List<Article> articles = new ArrayList<>();

    public void addContents(Article article) {
        long id = articles.size() + 1;
        article.setId(id);
        articles.add(article);
        System.out.println("Repository add");
    }

    public Optional<Article> findByIndex(long id) {
        return articles.stream().filter(user -> user.getId() == id).findFirst();
    }


    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }

    public void clearStore() {
        articles.clear();
    }
}
