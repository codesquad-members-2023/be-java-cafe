package kr.codesqaud.cafe.controller;


import kr.codesqaud.cafe.basic.Article;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    MemoryArticleRepository memoryArticleRepository;

    public ArticleController() {
        this.memoryArticleRepository = new MemoryArticleRepository();
    }

    @PostMapping("/create")
    public String create(@RequestParam String writer,
                         @RequestParam String title,
                         @RequestParam String contents) {
        int articleId = memoryArticleRepository.getSize() + 1;
        memoryArticleRepository.add(new Article(articleId, writer, title, contents));
        return "redirect:/qna/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Article> articles = new ArrayList<>(memoryArticleRepository.findAll());
        model.addAttribute("articles", articles);
        return "/qna/list";
    }

    @GetMapping("/show/{articleId}")
    public String show(@PathVariable int articleId,
                       Model model) {
        int articleIndex = articleId - 1;
        Article article = memoryArticleRepository.findByIndex(articleIndex);
        model.addAttribute(article);
        return "/qna/show";
    }

}
