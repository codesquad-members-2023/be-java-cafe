package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.domain.article.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/")
    public String listArticles(Model model) throws SQLException {
        List<Article> articles = articleRepository.showAllArticles();
        model.addAttribute("articles", articles);
        log.trace("게시글 갯수: {}", articles.size());
        return "index";
    }

    @GetMapping("/qna/questions")
    public String writeForm() {
        return "qna/form";
    }

    @PostMapping("/qna/questions")
    public String saveQuestion(@ModelAttribute("article") Article article) throws SQLException {
        articleRepository.write(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{articleSequence}")
    public String articleShow(@PathVariable Long articleSequence, Model model) throws SQLException {
        Article findArticle = articleRepository.findByArticleSequence(articleSequence);
        model.addAttribute("article", findArticle);
        log.trace("제목: {}, 글쓴이: {}, 내용: {}", findArticle.getTitle(), findArticle.getWriter(), findArticle.getContents());
        return "qna/show";
    }
}
