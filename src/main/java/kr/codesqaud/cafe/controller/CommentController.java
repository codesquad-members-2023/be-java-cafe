package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.repository.comment.CommentRepository;
import kr.codesqaud.cafe.util.SessionConstant;
import kr.codesqaud.cafe.util.ValidateConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class CommentController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping("/qna/{articleId}")
    public String addComment(@PathVariable long articleId,
                             @RequestParam String contents,
                             HttpSession httpSession,
                             Model model) {

        if (httpSession.getAttribute(SessionConstant.LOGIN_USERID) == null) {
            model.addAttribute("errorMessage", ValidateConstant.UNKNOWN_USER);
            return "util/error";
        }

        long writer = (long) httpSession.getAttribute(SessionConstant.LOGIN_USERID);
        commentRepository.save(new Comment(articleId, writer, contents));

        return "redirect:/qna/{articleId}";
    }

    @DeleteMapping("/qna/{articleId}/{commentId}")
    public String deleteComment(@PathVariable long articleId,
                                @PathVariable long commentId,
                                HttpSession httpSession,
                                Model model) {

        if (!validateIdentity(commentId, httpSession)) {
            model.addAttribute("errorMessage", ValidateConstant.NOT_YOURS);

            return "util/error";
        }

        LocalDateTime now = LocalDateTime.now();
        commentRepository.deleteComment(commentId, now);

        return "redirect:/qna/{articleId}";
    }

    private boolean validateIdentity(long commentId, HttpSession httpSession) {
        Comment targetComment = commentRepository.findByCommentId(commentId);

        return targetComment.getWriter() == (long) httpSession.getAttribute(SessionConstant.LOGIN_USERID);

    }
}
