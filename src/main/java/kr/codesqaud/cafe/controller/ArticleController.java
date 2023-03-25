package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.article.JdbcArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class ArticleController {

    private JdbcArticleRepository jdbcArticleRepository;

    Logger logger = LoggerFactory.getLogger(getClass());

    public ArticleController(JdbcArticleRepository jdbcArticleRepository) {
        this.jdbcArticleRepository = jdbcArticleRepository;
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
        Optional<Article> article = jdbcArticleRepository.findById(id);
        System.out.println(article.isPresent());
        if (article.isPresent()) {
            model.addAttribute("articles", article.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글이 없습니다.")));
            return "qna/show";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/articles/{id}/form")
    public String updatePost(@PathVariable("id") Long id, Model model, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
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
        Member value = (Member) session.getAttribute("sessionedUser");
        String loginId = value.getUserId();
        String writerId = article.getAuthorId();
        if (loginId != writerId) {
            throw new IllegalArgumentException();
        }
    }


}

