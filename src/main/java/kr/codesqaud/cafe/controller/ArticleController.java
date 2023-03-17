package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {

    private ArticleRepository articleRepository;

    Logger logger = LoggerFactory.getLogger(getClass());

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        logger.debug("articleController");
    }

    @PostMapping("/questions")
    public String addContents(@RequestParam String title, @RequestParam String writer, @RequestParam String contents){
        logger.debug("addContents");
        articleRepository.addContents(new Article(title,writer,contents));
        return "redirect:/";
    }

    @GetMapping("/")
    public String getContentList(Model model) {
        logger.debug("getContentList");
        List<Article> contentList = articleRepository.findAll();
        model.addAttribute("contents", contentList);
        return "index";
    }

    @GetMapping("/articles/{id}")
    public String getContent(@PathVariable Long id, Model model) {
        Optional<Article> article = articleRepository.findByIndex(id-1);
        if (article.isPresent()) {
            model.addAttribute("article", article.get());
            return "qna/show";
        } else {
            return "redirect:/";
        }
    }
}
