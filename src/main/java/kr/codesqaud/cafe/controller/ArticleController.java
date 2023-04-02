package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.article.ArticleResponse;
import kr.codesqaud.cafe.util.SessionUser;
import kr.codesqaud.cafe.dto.answer.AnswerResponseDto;
import kr.codesqaud.cafe.exception.InvalidAuthorityException;
import kr.codesqaud.cafe.exception.ManageArticleException;
import kr.codesqaud.cafe.repository.AnswerRepository;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import java.util.List;

import static kr.codesqaud.cafe.constant.ConstUrl.REDIRECT_INDEX;
import static kr.codesqaud.cafe.exception.InvalidAuthorityException.NO_SESSION_USER;
import static kr.codesqaud.cafe.exception.ManageArticleException.INVALID_WRITER;
import static kr.codesqaud.cafe.exception.ManageArticleException.NOT_POSSIBLE_DELETE;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final AnswerRepository answerRepository;

    public ArticleController(ArticleRepository articleRepository, MemberRepository memberRepository, AnswerRepository answerRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping("/questions")
    public String postQuestions(HttpSession httpSession, String title, String contents) {
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);
        Member member = memberRepository.findById(sessionUser.getId());
        Article article = new Article(member, title, contents);
        articleRepository.save(article);
        return REDIRECT_INDEX;
    }

    @DeleteMapping("/articles/{id}")
    public String deleteArticle(@PathVariable Long id, HttpSession httpSession) throws InvalidAuthorityException, ManageArticleException {
        ArticleResponse exArticle = articleRepository.findById(id);
        List<AnswerResponseDto> answerList = answerRepository.findAllByArticleId(id);
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);

        if (!sessionUser.equals(exArticle.getWriterIndex())) {
            throw new ManageArticleException(INVALID_WRITER);
        }

        if (answerList.stream().anyMatch(e -> e.getWriterId()!=sessionUser.getId())) {
            throw new ManageArticleException(NOT_POSSIBLE_DELETE);
        }

        answerRepository.deleteAll(id);
        articleRepository.delete(id);
        return REDIRECT_INDEX;
    }

    @GetMapping("/articles/{id}/update")
    public String updateArticleForm(@PathVariable long id, Model model, HttpSession httpSession) throws ManageArticleException {

        ArticleResponse article = articleRepository.findById(id);
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);

        if (!sessionUser.equals(article.getWriterIndex())) {
            throw new ManageArticleException(INVALID_WRITER);
        }

        model.addAttribute("article", article);
        return "qna/updateForm";
    }

    @PutMapping("/articles/{id}/update")
    public String updateArticle(Article newArticle, @PathVariable Long id, RedirectAttributes redirectAttributes, HttpSession httpSession) throws InvalidAuthorityException {
        ArticleResponse exArticle = articleRepository.findById(id);
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);

        if (!sessionUser.equals(exArticle.getWriterIndex())) {
            throw new InvalidAuthorityException(NO_SESSION_USER);
        }

        articleRepository.update(exArticle.getArticleIndex(), newArticle);
        redirectAttributes.addFlashAttribute("id", id);
        return "redirect:/articles/{id}";
    }

    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleRepository.findById(id));
        List<AnswerResponseDto> collect = answerRepository.findAllByArticleId(id);
        model.addAttribute("answers", collect);
        model.addAttribute("answerSize", collect.size());
        return "qna/show";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("list", articleRepository.findAll());
        return "index";
    }
}
