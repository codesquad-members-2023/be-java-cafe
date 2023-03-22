package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArticleController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/qna")
    public String addUser(@ModelAttribute Article article) {
        articleRepository.save(article);
        log.debug("debug log={}", article.getContents());
        return "redirect:/qna/list";
    }

    @GetMapping(value = {"/qna/list", "/"})
    public String findArticleList(Model model) {
        List<Article> articleList = articleRepository.findAllArticles();
        model.addAttribute("qnaList", articleList);
        log.info("debug log={}", articleList.size());
        return "/qna/list";
    }

    @GetMapping("/qna/{title}")
    public String findArticle(@PathVariable int id, Model model) {
        Article article = articleRepository.findByArticleId(id);
        model.addAttribute("qna", article);
        log.info("debug log={}", "findArticle");
        return "/qna/show";
    }
}
