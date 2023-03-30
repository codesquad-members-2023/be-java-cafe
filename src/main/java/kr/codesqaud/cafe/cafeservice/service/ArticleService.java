package kr.codesqaud.cafe.cafeservice.service;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.exhandler.exception.ArticleNotFoundException;
import kr.codesqaud.cafe.cafeservice.repository.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    public Article findById(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("article 찾을수 없습니다"));
    }
}
