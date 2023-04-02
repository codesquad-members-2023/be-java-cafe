package kr.codesqaud.cafe.cafeservice.controller;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import kr.codesqaud.cafe.cafeservice.domain.Reply;
import kr.codesqaud.cafe.cafeservice.dto.ArticleDTO;
import kr.codesqaud.cafe.cafeservice.repository.article.ArticleRepository;
import kr.codesqaud.cafe.cafeservice.repository.reply.ReplyRepository;
import kr.codesqaud.cafe.cafeservice.service.ArticleService;
import kr.codesqaud.cafe.cafeservice.service.ReplyService;
import kr.codesqaud.cafe.cafeservice.session.LoginSessionUtils;
import kr.codesqaud.cafe.cafeservice.session.SessionConst;
import kr.codesqaud.cafe.cafeservice.validator.ArticleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static kr.codesqaud.cafe.cafeservice.session.LoginSessionUtils.*;

@Controller
public class ArticleController {

    private final ArticleRepository repository;
    private final ArticleService service;
    private final ReplyRepository replyRepository;
    private final ReplyService replyService;
    private final ArticleValidator articleValidator;

    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    public ArticleController(ArticleRepository repository, ArticleService service,
                             ReplyRepository replyRepository, ReplyService replyService,
                             ArticleValidator articleValidator) {
        this.repository = repository;
        this.service = service;
        this.replyRepository = replyRepository;
        this.replyService = replyService;
        this.articleValidator = articleValidator;
    }

    @GetMapping("/")
    public String memberList(Model model) {
        List<Article> articles = repository.findAll();
        model.addAttribute("articles", articles);
        return "index";
    }

    @PostMapping("/articles")
    public String questions(@Validated @ModelAttribute ArticleDTO articleDTO, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            log.debug("errors = {}", bindingResult);
            return "redirect:/";
        }

        LoginSessionUtils sessionUtils = (LoginSessionUtils) session.getAttribute(SessionConst.LOGIN_MEMBER);
        System.out.println("sessionUtils = " + sessionUtils);
        //TODO 로그인한 사용자의 nickName이 게시글의 Writer
        ArticleDTO article = new ArticleDTO(sessionUtils.getId(), articleDTO.getWriter(), articleDTO.getTitle(), articleDTO.getContent());

        log.debug("article{}=", article);
        repository.save(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        Article article = service.findById(id);

        List<Reply> reply = replyService.findReplyList(article.getId());

        if (article == null) {
            return "qna/show_filed";
        }

        model.addAttribute("article", article);
        model.addAttribute("articleSize", reply.size());
        model.addAttribute("reply", reply);
        return "qna/show";
    }

    @DeleteMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable Long id, HttpSession session) {
        //Todo 세션아이디(로그인아이디)  Article의 FK키 와 비교
        Article article = service.findById(id);
        LoginSessionUtils sessionUtils = (LoginSessionUtils) session.getAttribute(SessionConst.LOGIN_MEMBER);
        sessionCheckId(article.getUserId(), sessionUtils);
        repository.delete(id);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}/update")
    public String updateArticleForm(@PathVariable Long id, Model model, HttpSession session) {
        LoginSessionUtils sessionUtils = (LoginSessionUtils) session.getAttribute(SessionConst.LOGIN_MEMBER);
        sessionCheckId(id, sessionUtils);
        Article findArticle = service.findById(id);
        model.addAttribute("article", findArticle);
        return "qna/updateForm";
    }

    @PutMapping("/articles/{id}/update")
    public String updateArticle(@Valid @ModelAttribute ArticleDTO articleDTO, BindingResult result,
                                @PathVariable Long id) {
        //TODO 자기가 작성한 글이 아니면 수정 할 수 없다.
        articleValidator.validate(articleDTO, result);

        Article findArticle = service.findById(id);
        repository.update(findArticle, articleDTO.getTitle(), articleDTO.getContent());
        return "redirect:/";
    }
}
