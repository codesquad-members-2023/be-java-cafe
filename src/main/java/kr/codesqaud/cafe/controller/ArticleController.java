package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private final MemoryArticleRepository repository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public ArticleController(MemoryArticleRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/questions")
    public String question(@ModelAttribute Article article) {
        repository.save(article);

        log.info("{}", article.getId());
        log.info("{}", article.getWriter());
        log.info("{}", article.getTitle());
        log.info("{}", article.getContents());

        return "redirect:/";
    }
}
