package kr.codesqaud.cafe.cafeservice.controller;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import kr.codesqaud.cafe.cafeservice.exhandler.exception.ArticleNotFoundException;
import kr.codesqaud.cafe.cafeservice.repository.article.ArticleRepository;
import kr.codesqaud.cafe.cafeservice.session.LoginSessionUtils;
import kr.codesqaud.cafe.cafeservice.session.SessionConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static kr.codesqaud.cafe.cafeservice.session.LoginSessionUtils.*;

@Controller
public class ArticleController {

    private final ArticleRepository repository;
    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    public ArticleController(ArticleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String memberList(Model model) {
        List<Article> articles = repository.findAll();
        model.addAttribute("articles", articles);
        return "index";
    }

    @PostMapping("/questions")
    public String questions(@ModelAttribute Article article) {
        log.debug("article{}=", article);
        repository.save(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        Article article = validFindById(id, "글을 찾을 수 없습니다.");
        model.addAttribute("article", article);
        return "qna/show";

    }

    @DeleteMapping("/questions/{id}delete")
    public String deleteArticle(@PathVariable Long id, HttpSession session) {
        //Todo 세션아이디(로그인아이디)  Article의 FK키 와 비교
        Article article = validFindById(id, "글을 찾을 수 없습니다.");
        LoginSessionUtils sessionUtils = (LoginSessionUtils) session.getAttribute(SessionConst.LOGIN_MEMBER);
        sessionCheckId(id, sessionUtils);
        repository.delete(id);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}/update")
    public String updateArticleForm(@PathVariable Long id, Model model, HttpSession session) {
        LoginSessionUtils sessionUtils = (LoginSessionUtils) session.getAttribute(SessionConst.LOGIN_MEMBER);
        sessionCheckId(id, sessionUtils);
        Article article = validFindById(id, "글을 찾을 수 없습니다.");
        model.addAttribute("article", article);
        return "qna/updateForm";
    }

    @PutMapping("/articles/{id}/update")
    public String updateArticle(@RequestParam String title, @RequestParam String content,
                                @PathVariable Long id) {
        //TODO 자기가 작성한 글이 아니면 수정 할 수 없다.
        Article article = validFindById(id, "수정할 수 없습니다");
        repository.update(article, title, content);
        return "redirect:/";
    }

    private Article validFindById(Long id, String message) {
        Optional<Article> findArticle = repository.findById(id);
        return findArticle.orElseThrow(() -> new ArticleNotFoundException(message));
    }
}
