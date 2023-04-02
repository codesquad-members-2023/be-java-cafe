package kr.codesqaud.cafe.cafeservice.repository.article;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import kr.codesqaud.cafe.cafeservice.dto.ArticleDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    final List<ArticleDTO> store = new ArrayList<>();

    // TODO article 저장, 회원가입에 저장된 회원의 iD와 article의 글쓴이와 일치하는지 확인.
    @Override
    public void save(ArticleDTO article) {
        store.add(article);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Article> findAll() {
        return null;
    }


    @Override
    public void delete(Long id) {
    }

    @Override
    public void update(Article article, String title, String content) {
    }

    @Override
    public void findReplyList() {

    }

    public Article findOne(Long id) {
        return findById(id).orElseThrow();
    }
}
