package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.repository.comment.CommentRepository;
import kr.codesqaud.cafe.util.ValidateConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

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

        if (httpSession.getAttribute("loggedInId") == null) {
            model.addAttribute("errorMessage", ValidateConst.UNKNOWN_USER);
            return "util/error";
        }

        long writer = (long) httpSession.getAttribute("loggedInId");
        commentRepository.save(new Comment(articleId, writer, contents));

        return "redirect:/qna/{articleId}";
    }
}
