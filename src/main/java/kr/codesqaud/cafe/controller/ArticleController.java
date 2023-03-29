package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.repository.article.JdbcArticleRepository;
import kr.codesqaud.cafe.repository.reply.JdbcReplyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;


@Controller
public class ArticleController {

    private JdbcArticleRepository jdbcArticleRepository;
    private JdbcReplyRepository jdbcReplyRepository;

    Logger logger = LoggerFactory.getLogger(getClass());

    public ArticleController(JdbcArticleRepository jdbcArticleRepository, JdbcReplyRepository jdbcReplyRepository) {
        this.jdbcArticleRepository = jdbcArticleRepository;
        this.jdbcReplyRepository = jdbcReplyRepository;
        logger.debug("articleController");
    }

    @PostMapping("/questions")
    public String save(@ModelAttribute Article article) {
        logger.debug("addContents {}", LocalDateTime.now());
        jdbcArticleRepository.save(article);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getContentList(Model model) {
        logger.debug("getContentList");
        List<Article> contentList = jdbcArticleRepository.findAll();
        model.addAttribute("contents", contentList);
        return "index";
    }

    @GetMapping("/articles/{id}")
    public String getContent(@PathVariable Long id, Model model) {
        Article article = jdbcArticleRepository.findById(id).orElseThrow();
        List<Reply> replies = jdbcReplyRepository.findAll(article.getId());
        model.addAttribute("replies",replies);
        model.addAttribute("articles",article);
        return "qna/show";
    }


    @GetMapping("/articles/{id}/form")
    public String updatePost(@PathVariable("id") Long id, Model model, HttpSession session) {
        Article writer = jdbcArticleRepository.findById(id).orElseThrow(null);
        vaildateAuthorization(session, writer);
        model.addAttribute("modified", writer);
        return "qna/modifiedForm";
    }

    @PutMapping("/articles/{id}")
    public String updatePost(@ModelAttribute("article") Article article) {
        jdbcArticleRepository.update(article);
        return "redirect:/";
    }

    @DeleteMapping("/articles/{id}/delete")
    public String deletePost(@PathVariable Long id,HttpSession session) {
        Article writer = jdbcArticleRepository.findById(id).orElseThrow(null);
        vaildateAuthorization(session, writer);
        jdbcArticleRepository.delete(id);
        return "redirect:/";
    }

    public void vaildateAuthorization(HttpSession session, Article article) {
        Member value = (Member) session.getAttribute(MemberSessionUser.LOGIN_MEMBER);
        String loginId = value.getUserId();
        String writerId = article.getAuthorId();
        if (loginId != writerId) {
            throw new IllegalArgumentException();
        }
    }


}

