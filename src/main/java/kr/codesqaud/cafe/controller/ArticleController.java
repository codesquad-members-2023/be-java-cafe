package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.repository.NamedJdbcTemplateArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static kr.codesqaud.cafe.Constants.SESSIONED_USER;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
public class ArticleController {
	private final NamedJdbcTemplateArticleRepository articleRepository;

	private final Logger log = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	public ArticleController(NamedJdbcTemplateArticleRepository articleRepository) {
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
	public String writeForm(HttpSession session) {
		if (session.getAttribute(SESSIONED_USER) == null) {
			return "user/login";
		}
		return "qna/form";
	}

	@PostMapping("/qna/questions")
	public String saveQuestion(@ModelAttribute("article") Article article) throws SQLException {
		articleRepository.write(article);
		return "redirect:/";
	}

	@GetMapping("/articles/{articleSequence}")
	public String articleShow(HttpSession session, @PathVariable Long articleSequence, Model model) throws SQLException {
		if (session.getAttribute(SESSIONED_USER) == null) {
			return "user/login";
		}
		Article findArticle = articleRepository.findByArticleSequence(articleSequence);
		model.addAttribute("article", findArticle);
		log.trace("제목: {}, 글쓴이: {}, 내용: {}", findArticle.getTitle(), findArticle.getWriter(), findArticle.getContents());
		return "qna/show";
	}
}
