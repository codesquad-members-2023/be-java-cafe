package kr.codesqaud.cafe.controller;


import kr.codesqaud.cafe.basic.Article;
import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.config.ConstConfig;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/createForm")
    public String create(@SessionAttribute(name = ConstConfig.SESSION_ID) User user, Model model) {
        // TODO : 뷰 템플렛에 User 객체에 대한 정보를 넘겨줄 필요성이 없어보이므로, 제거 할 예정
        model.addAttribute("user", user);
        return "qna/createForm";
    }

    @PostMapping("/create")
    public String create(@RequestParam String title,
                         @RequestParam String contents,
                         @SessionAttribute(name = ConstConfig.SESSION_ID) User user) {
        articleRepository.save(new Article(user.getUserId(), title, contents));
        return "redirect:/qna/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Article> articles = new ArrayList<>(articleRepository.findAll());

        model.addAttribute("articles", articles);
        return "qna/list";
    }

    @GetMapping("/show/{articleId}")
    public String show(@PathVariable int articleId,
                       Model model) {
        Article article = articleRepository.findByArticleId(articleId);
        model.addAttribute(article);
        return "qna/show";
    }

    @GetMapping("/updateForm/{articleId}")
    public String update(@PathVariable int articleId,
                         Model model) {
        Article article = articleRepository.findByArticleId(articleId);
        model.addAttribute(article);
        return "qna/updateForm";
    }

    @PostMapping("/update/{articleId}")
    public String update(@PathVariable int articleId,
                         @RequestParam String title,
                         @RequestParam String contents) {
        articleRepository.update(articleId, title, contents);
        return "redirect:/qna/show/" + articleId;
    }

    @DeleteMapping("/delete/{articleId}")
    public String delete(@PathVariable int articleId) {
        articleRepository.delete(articleId);
        return "redirect:/qna/list";
    }


}
