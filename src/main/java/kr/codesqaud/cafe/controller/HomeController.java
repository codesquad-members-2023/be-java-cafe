package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.JdbcTemplateArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final JdbcTemplateArticleRepository repository;

    @Autowired
    public HomeController(JdbcTemplateArticleRepository repository) {
        this.repository = repository;
    }

    // 질문하기 목록 Mapping(Home)
    @GetMapping("/")
    public String showBoard(@SessionAttribute(name = "loginUser", required = false)
                            User loginUser, Model model) {
        log.debug("내가 싼 글(똥) 목록");

        // 로그인 여부 확인
        if (loginUser != null) {
            log.debug("로그인 중");
        }

        // 질문글 출력
        List<Article> articles = repository.findAllArticle();
        model.addAttribute("articles", articles);

        return "index";
    }
}
