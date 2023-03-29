package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.dto.ArticleWithWriterDto;
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
		List<ArticleWithWriterDto> articles = articleRepository.showAllArticles();
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
	public String saveQuestion(@RequestParam String title, @RequestParam String contents, HttpSession session) throws SQLException {
		Article article = new Article((Long) session.getAttribute(SESSIONED_USER), title, contents);
		articleRepository.write(article);
		return "redirect:/";
	}

	@PutMapping("/articles/{articleSequence}/edit")
	public String updateQuestion(@RequestParam String title, @RequestParam String contents, @PathVariable Long articleSequence) {
		ArticleWithWriterDto articleWithWriterDto = new ArticleWithWriterDto(articleSequence, title, contents);
		System.out.println("바뀐 제목: " + articleWithWriterDto.getTitle());
		System.out.println("바뀐 내용: " + articleWithWriterDto.getContents());
		System.out.println("글 번호: " + articleWithWriterDto.getArticleSequence());

		articleRepository.update(articleWithWriterDto);

		return "redirect:/articles/{articleSequence}";
	}

	@GetMapping("/articles/{articleSequence}")
	public String articleShow(HttpSession session, @PathVariable Long articleSequence, Model model) throws SQLException {
		if (session.getAttribute(SESSIONED_USER) == null) {
			return "user/login";
		}
		ArticleWithWriterDto findArticle = articleRepository.findByArticleSequence(articleSequence);
		model.addAttribute("article", findArticle);
		return "qna/show";
	}

	@GetMapping("/articles/{articleSequence}/edit")
	public String articleEditForm(HttpSession session, @PathVariable Long articleSequence, Model model) {
		if (session.getAttribute(SESSIONED_USER) == null) {
			System.out.println("비어있는 세션");
			return "user/login";
		}

		ArticleWithWriterDto findArticle = articleRepository.findByArticleSequence(articleSequence);
		if (findArticle.getUserSequence() != session.getAttribute(SESSIONED_USER)) {
			System.out.println("다른 유저의 글입니다!");
			return "redirect:/articles/{articleSequence}";
		}

		System.out.println("제목: " + findArticle.getTitle());
		System.out.println("글쓴이: " + findArticle.getWriter());
		System.out.println("사용자 번호: " + findArticle.getUserSequence());
		System.out.println("내용: " + findArticle.getContents());
		model.addAttribute("findArticle", findArticle);

		return "qna/editForm";
	}

	@DeleteMapping("/articles/{articleSequence}/delete")
	public String articleDelete(HttpSession session, @PathVariable Long articleSequence) {
		ArticleWithWriterDto article = articleRepository.findByArticleSequence(articleSequence);
		if (session.getAttribute(SESSIONED_USER) != article.getUserSequence()) {
			System.out.println("다른 사람의 글은 삭제 불가능!!!");
			return "redirect:/articles/{articleSequence}";
		}

		articleRepository.delete(articleRepository.findByArticleSequence(articleSequence));
		return "redirect:/";
	}

}
