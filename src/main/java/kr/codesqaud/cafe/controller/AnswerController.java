package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Answer;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.SessionUser;
import kr.codesqaud.cafe.repository.AnswerRepository;
import kr.codesqaud.cafe.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class AnswerController {
    AnswerRepository answerRepository;
    MemberRepository memberRepository;

    public AnswerController(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @PostMapping("/articles/{articleId}/answers")
    public String saveAnswer(@PathVariable long articleId, Answer answer, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);
        Member writer = memberRepository.findById(sessionUser.getId());
        answer.setWriter(writer);
        answer.setArticleId(articleId);
        answerRepository.save(answer);
        return "redirect:/articles/{articleId}";
    }

    @PutMapping("/articles/{articleId}/answers/{answerId}")
    public String updateAnswer(@PathVariable long articleId, Answer answer, RedirectAttributes redirectAttributes) {

        return "";
    }

    @DeleteMapping("/articles/{articleId}/answers/{answerId}")
    public String deleteAnswer() {
        return "";
    }
}
