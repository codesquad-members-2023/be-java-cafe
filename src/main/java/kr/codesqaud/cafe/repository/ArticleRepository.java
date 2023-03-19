package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {

    private static List<Article> articles = new ArrayList<>();
    Logger logger = LoggerFactory.getLogger(getClass());

    public void addContents(Article article) {
        logger.debug("addContents");
        article.setId((long) (articles.size() + 1));
        articles.add(article);
    }

    public Optional<Article> findByIndex(long id) {
        return articles.stream().filter(user -> user.getId() == id).findFirst();
    }


    public List<Article> findAll() {
        return new ArrayList<>(Collections.unmodifiableList(articles));
    }

    public void clearStore() {
        articles.clear();
    }
}
