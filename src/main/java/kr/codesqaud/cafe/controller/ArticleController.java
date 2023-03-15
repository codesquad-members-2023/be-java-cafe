package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final MemoryArticleRepository repository;

    @Autowired
    public ArticleController(MemoryArticleRepository repository) {
        this.repository = repository;
    }

    // 질문하기 Mapping
    @GetMapping("/qna/form.html")
    public String showQnaForm() {
        log.info("글쓰기로 들어왔는감?!");

        return "qna/form";
    }

    // 질문하기 POST
    @PostMapping("/questions")
    public String writing(@ModelAttribute Article article) {
        log.info("글쓰기전 5분동안 생각하기!");

        repository.save(article);
        return "redirect:/";
    }
}
