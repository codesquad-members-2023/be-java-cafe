package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Answer;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.answer.AnswerResponseDto;
import kr.codesqaud.cafe.exception.ManageArticleException;
import kr.codesqaud.cafe.util.SessionUser;
import kr.codesqaud.cafe.repository.AnswerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static kr.codesqaud.cafe.exception.ManageArticleException.INVALID_WRITER;

@Controller
public class AnswerController {
    private static final String REDIRECT_ARTICLE = "redirect:/articles/{articleId}";

    AnswerRepository answerRepository;

    public AnswerController(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @PostMapping("/articles/{articleId}/answers")
    public String saveAnswer(@PathVariable long articleId, Answer answer, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);

        answer.setWriter(new Member(sessionUser.getId(), sessionUser.getNickName()));
        answer.setArticleId(articleId);
        answerRepository.save(answer);

        redirectAttributes.addFlashAttribute("articleId", articleId);
        return REDIRECT_ARTICLE;
    }

    // TODO 모르고 댓글 수정을 만들었다.... 하지만 뷰가 없다....
    @PutMapping("/articles/{articleId}/answers/{answerId}")
    public String updateAnswer(@PathVariable long articleId, @PathVariable long answerId, Answer answer, HttpSession httpSession, RedirectAttributes redirectAttributes) throws ManageArticleException {
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);
        AnswerResponseDto exAnswer = answerRepository.findById(answerId);

        if (!sessionUser.equals(exAnswer.getWriterId())) {
            throw new ManageArticleException(INVALID_WRITER);
        }

        answerRepository.update(answerId, answer.getContents());
        redirectAttributes.addFlashAttribute("articleId", articleId);
        return REDIRECT_ARTICLE;
    }

    @DeleteMapping("/articles/{articleId}/answers/{answerId}")
    public String deleteAnswer(@PathVariable long articleId, @PathVariable long answerId, HttpSession httpSession, RedirectAttributes redirectAttributes) throws ManageArticleException {
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);
        AnswerResponseDto exAnswer = answerRepository.findById(answerId);

        if (!sessionUser.equals(exAnswer.getWriterId())) {
            throw new ManageArticleException(INVALID_WRITER);
        }

        answerRepository.delete(answerId);
        redirectAttributes.addFlashAttribute("articleId", articleId);
        return REDIRECT_ARTICLE;
    }
}
