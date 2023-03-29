package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Answer;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.SessionUser;
import kr.codesqaud.cafe.exception.ExceptionStatus;
import kr.codesqaud.cafe.exception.InvalidAuthorityException;
import kr.codesqaud.cafe.repository.AnswerRepository;
import kr.codesqaud.cafe.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static kr.codesqaud.cafe.constant.ConstUrl.REDIRECT_ARTICLE;

@Controller
public class AnswerController {
    AnswerRepository answerRepository;
    MemberRepository memberRepository;

    public AnswerController(AnswerRepository answerRepository, MemberRepository memberRepository) {
        this.answerRepository = answerRepository;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/articles/{articleId}/answers")
    public String saveAnswer(@PathVariable long articleId, Answer answer, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);

        Member writer = memberRepository.findById(sessionUser.getId());
        answer.setWriter(writer);
        answer.setArticleId(articleId);
        answerRepository.save(answer);

        redirectAttributes.addFlashAttribute("articleId", articleId);
        return REDIRECT_ARTICLE;
    }

    // TODO 모르고 댓글 수정을 만들었다.... 하지만 뷰가 없다....
    @PutMapping("/articles/{articleId}/answers/{answerId}")
    public String updateAnswer(@PathVariable long articleId, @PathVariable long answerId, Answer answer, HttpSession httpSession, RedirectAttributes redirectAttributes) throws InvalidAuthorityException {
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);
        Answer exAnswer = answerRepository.findById(answerId);

        if (!sessionUser.equals(exAnswer.getWriterId())) {
            throw new InvalidAuthorityException(ExceptionStatus.INVALID_WRITER);
        }

        answerRepository.update(answer, answerId);
        redirectAttributes.addFlashAttribute("articleId", articleId);
        return REDIRECT_ARTICLE;
    }

    @DeleteMapping("/articles/{articleId}/answers/{answerId}")
    public String deleteAnswer(@PathVariable long articleId, @PathVariable long answerId, HttpSession httpSession, RedirectAttributes redirectAttributes) throws InvalidAuthorityException {
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);
        Answer exAnswer = answerRepository.findById(answerId);

        if (!sessionUser.equals(exAnswer.getWriterId())) {
            throw new InvalidAuthorityException(ExceptionStatus.INVALID_WRITER);
        }

        answerRepository.delete(answerId);
        redirectAttributes.addFlashAttribute("articleId", articleId);
        return REDIRECT_ARTICLE;
    }
}
