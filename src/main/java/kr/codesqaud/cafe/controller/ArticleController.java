package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static kr.codesqaud.cafe.constant.ConstUrl.REDIRECT_INDEX;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    Logger logger = LoggerFactory.getLogger(ArticleController.class);

    public ArticleController(ArticleRepository articleRepository, MemberRepository memberRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/questions")
    public String postQuestions(long writerId, String title, String contents) {
        Member member = memberRepository.findById(writerId);
        Article article = new Article(member, title, contents);
        articleRepository.save(article);
        return REDIRECT_INDEX;
    }

    @GetMapping("/articles/{id}/update")
    public String updateArticleForm(@PathVariable long id, Model model) {
        model.addAttribute("article", articleRepository.findById(id));
        return "qna/updateForm";
    }

    @PutMapping("/articles/{id}/update")
    public String updateArticle(Article newArticle, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        Article exArticle = articleRepository.findById(id);
        articleRepository.update(exArticle, newArticle);
        redirectAttributes.addFlashAttribute("id", id);
        return "redirect:/articles/{id}";
    }

    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleRepository.findById(id));
        return "qna/show";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("list", articleRepository.findAll());
        return "index";
    }
}
