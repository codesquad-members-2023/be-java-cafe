package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.JdbcTemplateArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class ArticleController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final JdbcTemplateArticleRepository repository;

    @Autowired
    public ArticleController(JdbcTemplateArticleRepository repository) {
        this.repository = repository;
    }

    // 질문하기 POST
    @PostMapping("/questions")
    public String writing(@ModelAttribute Article article, HttpSession session) {
        log.debug("글쓰기전 5분동안 생각하기!");

        // 현재 로그인 유저Id 게시글에 set
        User sessionUser = (User) session.getAttribute("loginUser");
        article.setWriter(sessionUser.getUserId());

        repository.saveArticle(article);
        return "redirect:/";
    }

    // 질문 상세보기 Mapping
    @GetMapping("/articles/{id}")
    public String showBoardDetails(Model model, @PathVariable long id) {
        Optional<Article> article = repository.findById(id);

        // 질문글 유무 확인후 성공/실패 넘겨주기
        if (article.isPresent()) {
            log.debug("질문글 Mapping: 맵핑 성공!!!!");
            model.addAttribute("article", article.get());
            return "qna/show";
        }
        log.debug("질문글 Mapping: 맵핑 실패 ㅠㅠㅠ");
        return "qna/show_failed";
    }

    // 질문 수정하기 Mapping
    @GetMapping("/articles/{id}/update")
    public String editing(HttpSession session, @PathVariable long id) {

        User sessionUser = (User) session.getAttribute("loginUser");
        Article checkId = repository.findById(id)
                .filter(article -> article.getWriter().equals(sessionUser.getUserId()))
                .orElse(null);
        if (checkId == null || !checkId.getWriter().equals(sessionUser.getUserId())) {
            log.info("본인글이 아닙니다!!! 떽!!!!");
            return "error";
        }

        log.info("본인글이니 어서 수정하시죠 도죠도죠");
        return "qna/updateForm";
    }

    // 게시글 수정 PUT
    @PutMapping("/articles/{id}/update")
    public String updateArticle(@ModelAttribute Article article, @PathVariable long id) {
        System.out.println(article.getId());
        article.setId(id);
        if(!repository.updateArticle(article)){
            log.info("게시글 수정 실패ㅠ(해당하는 인덱스 없음)");
            return "redirect:/"; // TODO 전용 에러 페이지 만들기(게시글 수정)
        }

        log.info("게시글 수정 성공");
        return "redirect:/";
    }

    // 게시글 삭제 DELETE
    @DeleteMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable long id) {
        if (!repository.deleteArticle(id)) {
            log.info("삭제 실패(이미 지워짐)");
            return "redirect:/"; // TODO 전용 에러 페이지 만들기(게시글 삭제)
        }

        log.info("삭제 성공");
        return "redirect:/";
    }
}
