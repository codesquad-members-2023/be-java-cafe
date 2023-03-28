package kr.codesqaud.cafe.controller;


import kr.codesqaud.cafe.basic.Article;
import kr.codesqaud.cafe.basic.Reply;
import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.config.ConstConfig;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.DataBaseRepository.DatabaseReplyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    ArticleRepository articleRepository;
    DatabaseReplyRepository replyRepository;

    public ArticleController(ArticleRepository articleRepository, DatabaseReplyRepository replyRepository) {
        this.articleRepository = articleRepository;
        this.replyRepository = replyRepository;
    }

    @GetMapping("/create")
    public String create(@SessionAttribute(name = ConstConfig.SESSION_ID) User user, Model model) {
        return "qna/create";
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
        List<Reply> replies = replyRepository.findByArticleId(articleId);
        model.addAttribute("article", article);
        model.addAttribute("replies", replies);
        return "qna/show";
    }

    @GetMapping("/updateForm/{articleId}")
    public String update(@PathVariable int articleId,
                         @SessionAttribute(name = ConstConfig.SESSION_ID) User user,
                         Model model) {
        // TODO : 검증 공부 후 다시 리펙토링 할 예정
        Article article = articleRepository.findByArticleId(articleId);
        if (!article.getWriter().equals(user.getUserId())) {
            return "redirect:/qna/list";
        }

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
    public String delete(@PathVariable int articleId,
                         @SessionAttribute(name = ConstConfig.SESSION_ID) User user) {
        Article article = articleRepository.findByArticleId(articleId);
        // TODO : 검증 공부 후 다시 리펙토링 할 예정
        if (article.getWriter().equals(user.getUserId())) {
            articleRepository.delete(articleId);
        }
        return "redirect:/qna/list";
    }


}
