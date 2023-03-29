package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

import static kr.codesqaud.cafe.service.SessionUtil.getUserInfo;

@Controller
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final ArticleService articleService;

    @Autowired
    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 질문하기 목록 Mapping(Home)
    @GetMapping("/")
    public String showBoard(HttpSession session, Model model) {
        log.debug("게시글 목록: 내가 싼 글(똥)");

        // 로그인 여부 확인
        User loginUser = (User) getUserInfo(session);
        if (loginUser != null) {
            log.debug("로그인: 로그인중");
        }

        // 게시글 출력
        List<Article> articles = articleService.findAllArticle();
        model.addAttribute("articles", articles);

        return "index";
    }
}
