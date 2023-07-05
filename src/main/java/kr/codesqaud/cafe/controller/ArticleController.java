package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.domain.reply.ReplyService;
import kr.codesqaud.cafe.dto.ArticleWithWriterDto;
import kr.codesqaud.cafe.dto.ArticleWithoutContentsDto;
import kr.codesqaud.cafe.dto.ReplyDto;
import kr.codesqaud.cafe.repository.NamedJdbcTemplateArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static kr.codesqaud.cafe.utility.Constants.SESSIONED_USER;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
public class ArticleController {
	private final NamedJdbcTemplateArticleRepository articleRepository;
	private final ReplyService replyService;

	private final Logger log = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	public ArticleController(NamedJdbcTemplateArticleRepository articleRepository, ReplyService replyService) {
		this.replyService = replyService;
		this.articleRepository = articleRepository;
	}

	@GetMapping("/")
	public String listArticles(Model model) throws SQLException {
		List<ArticleWithoutContentsDto> articles = articleRepository.findAllArticlesWithoutContents();
		model.addAttribute("articles", articles);
		log.trace("게시글 갯수: {}", articles.size());
		return "index";
	}

	@GetMapping("/qna/questions/form")
	public String writeForm() {
		return "qna/form";
	}

	@PostMapping("/qna/questions/form")
	public String saveQuestion(@RequestParam String title, @RequestParam String contents, HttpSession session) throws SQLException {
		Article article = new Article((Long) getAttributeFromSession(session), title, contents);
		articleRepository.write(article);
		return "redirect:/";
	}

	@PutMapping("/articles/{articleSequence}/edit")
	public String updateQuestion(@RequestParam String title, @RequestParam String contents, @PathVariable Long articleSequence) {
		ArticleWithWriterDto articleWithWriterDto = new ArticleWithWriterDto(articleSequence, title, contents);

		log.info("글 번호: {}, 제목: {}, 내용: {}", articleWithWriterDto.getArticleSequence(), articleWithWriterDto.getTitle(), articleWithWriterDto.getContents());

		articleRepository.update(articleWithWriterDto);

		return "redirect:/articles/{articleSequence}";
	}

	@GetMapping("/articles/{articleSequence}")
	public String articleShow(@PathVariable Long articleSequence, Model article, Model replies) throws SQLException {
		ArticleWithWriterDto findArticle = articleRepository.findByArticleSequence(articleSequence);
		List<ReplyDto> replyDtoList = replyService.showReplies(articleSequence);
		replies.addAttribute("replies", replyDtoList);
		article.addAttribute("article", findArticle);
		return "qna/show";
	}

	@GetMapping("/articles/{articleSequence}/edit")
	public String articleEditForm(HttpSession session, @PathVariable Long articleSequence, Model model) {
		ArticleWithWriterDto findArticle = articleRepository.findByArticleSequence(articleSequence);
		if (findArticle.getUserSequence() != getAttributeFromSession(session)) {
			log.info("다른 유저의 글 !!");
			return "redirect:/articles/{articleSequence}";
		}

		model.addAttribute("findArticle", findArticle);

		return "qna/editForm";
	}

	@DeleteMapping("/articles/{articleSequence}/delete")
	public String articleDelete(HttpSession session, @PathVariable Long articleSequence) {
		ArticleWithWriterDto article = articleRepository.findByArticleSequence(articleSequence);
		if (getAttributeFromSession(session) != article.getUserSequence()) {
			log.info("다른 유저의 글은 삭제 불가능 !!");
			return "redirect:/articles/{articleSequence}";
		}
		articleRepository.delete(articleRepository.findByArticleSequence(articleSequence));
		log.info("삭제 완료 !! 삭제 후 게시글 수: {}", articleRepository.findAllArticlesWithoutContents().size());
		return "redirect:/";
	}

	private Object getAttributeFromSession(HttpSession session) {
		return session.getAttribute(SESSIONED_USER);
	}

}
