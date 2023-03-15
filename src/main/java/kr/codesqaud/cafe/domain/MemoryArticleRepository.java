package kr.codesqaud.cafe.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.user.Article;

public class MemoryArticleRepository implements ArticleRepository {
    private List<Article> articleList = new ArrayList<>();

    public void addArticle(Article article) {
        articleList.add(article);
    }
}
