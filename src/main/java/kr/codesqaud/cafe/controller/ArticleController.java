package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.article.Reply;
import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.article.Writer;
import kr.codesqaud.cafe.dto.article.ArticleResponse;
import kr.codesqaud.cafe.util.SessionUser;
import kr.codesqaud.cafe.dto.answer.AnswerResponseDto;
import kr.codesqaud.cafe.exception.InvalidAuthorityException;
import kr.codesqaud.cafe.exception.ManageArticleException;

import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.MemberRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;


import static kr.codesqaud.cafe.constant.ConstUrl.REDIRECT_INDEX;
import static kr.codesqaud.cafe.exception.InvalidAuthorityException.NO_SESSION_USER;
import static kr.codesqaud.cafe.exception.ManageArticleException.INVALID_WRITER;
import static kr.codesqaud.cafe.exception.ManageArticleException.NOT_POSSIBLE_DELETE;

@Controller
public class ArticleController {

    private static final String REDIRECT_ARTICLE = "redirect:/articles/{articleId}";

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleController(ArticleRepository articleRepository, MemberRepository memberRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/articles/{articleId}/answers")
    public String saveAnswer(@PathVariable long articleId, Reply answer, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);

        answer.setWriter(new Writer(sessionUser.getId(), sessionUser.getNickName()));
        answer.setArticleId(articleId);
        articleRepository.saveReply(answer);

        redirectAttributes.addFlashAttribute("articleId", articleId);
        return REDIRECT_ARTICLE;
    }

    // TODO 모르고 댓글 수정을 만들었다.... 하지만 뷰가 없다....
    @PutMapping("/articles/{articleId}/answers/{answerId}")
    public String updateAnswer(@PathVariable long articleId, @PathVariable long answerId, Reply answer, HttpSession httpSession, RedirectAttributes redirectAttributes) throws ManageArticleException {
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);
        AnswerResponseDto exAnswer = articleRepository.findReplyById(answerId);

        if (!sessionUser.equals(exAnswer.getWriterId())) {
            throw new ManageArticleException(INVALID_WRITER);
        }

        articleRepository.updateReply(answerId, answer.getContents());
        redirectAttributes.addFlashAttribute("articleId", articleId);
        return REDIRECT_ARTICLE;
    }

    @DeleteMapping("/articles/{articleId}/answers/{answerId}")
    public String deleteAnswer(@PathVariable long articleId, @PathVariable long answerId, HttpSession httpSession, RedirectAttributes redirectAttributes) throws ManageArticleException {
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);
        AnswerResponseDto exAnswer = articleRepository.findReplyById(answerId);

        if (!sessionUser.equals(exAnswer.getWriterId())) {
            throw new ManageArticleException(INVALID_WRITER);
        }

        articleRepository.deleteAReply(answerId);
        redirectAttributes.addFlashAttribute("articleId", articleId);
        return REDIRECT_ARTICLE;
    }

    @PostMapping("/questions")
    public String postQuestions(HttpSession httpSession, String title, String contents) {
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);
        Member member = memberRepository.findById(sessionUser.getId());

        articleRepository.save(new Article(new Writer(member.getId(), member.getNickname()), title, contents));
        return REDIRECT_INDEX;
    }

    @Transactional(rollbackFor = {DataAccessException.class, SQLException.class})
    @DeleteMapping("/articles/{id}")
    public String deleteArticle(@PathVariable Long id, HttpSession httpSession) throws InvalidAuthorityException, ManageArticleException, SQLException {
        ArticleResponse exArticle = articleRepository.findById(id);
        List<AnswerResponseDto> answerList = articleRepository.findReplyByArticleId(id);
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);

        if (!sessionUser.equals(exArticle.getWriterIndex())) {
            throw new ManageArticleException(INVALID_WRITER);
        }

        if (answerList.stream().anyMatch(e -> e.getWriterId()!=sessionUser.getId())) {
            throw new ManageArticleException(NOT_POSSIBLE_DELETE);
        }

        articleRepository.deleteAllReply(id);
        int deleteCount = articleRepository.delete(id);

        if (deleteCount!=1) {
            throw new SQLException("잘못된 삭제 요청입니다.");
        }

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
        List<AnswerResponseDto> collect = articleRepository.findReplyByArticleId(id);
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
