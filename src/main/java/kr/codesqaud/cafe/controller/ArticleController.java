package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.NoSuchElementException;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;

    Logger logger = LoggerFactory.getLogger(ArticleController.class);

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/questions")
    public String postQuestions(String writer, String title, String contents) {
        Article article = new Article(writer, title, contents);
        logger.info(article.getWriter());
        articleRepository.save(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당하는 글이 없습니다.")));
        return "qna/show";
    }
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("list", articleRepository.findAll());
        return "index";
    }
}
