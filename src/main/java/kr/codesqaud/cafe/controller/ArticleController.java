package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.repository.article.JdbcArticleRepository;
import kr.codesqaud.cafe.repository.reply.JdbcReplyRepository;
import kr.codesqaud.cafe.util.MemberSessionUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;


@Controller
public class ArticleController {

    private JdbcArticleRepository jdbcArticleRepository;
    private JdbcReplyRepository jdbcReplyRepository;

    Logger logger = LoggerFactory.getLogger(getClass());

    public ArticleController(JdbcArticleRepository jdbcArticleRepository, JdbcReplyRepository jdbcReplyRepository) {
        this.jdbcArticleRepository = jdbcArticleRepository;
        this.jdbcReplyRepository = jdbcReplyRepository;
        logger.debug("articleController");
    }

    @PostMapping("/questions")
    public String save(@ModelAttribute Article article) {
        logger.debug("addContents {}", LocalDateTime.now());
        jdbcArticleRepository.save(article);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getContentList(Model model) {
        logger.debug("getContentList");
        List<Article> contentList = jdbcArticleRepository.findAll();
        model.addAttribute("contents", contentList);
        return "index";
    }

    @GetMapping("/articles/{id}")
    public String getContent(@PathVariable Long id, Model model) {
        Article article = jdbcArticleRepository.findById(id).orElseThrow();
        List<Reply> replies = jdbcReplyRepository.findAll(article.getId());
        model.addAttribute("replies",replies);
        model.addAttribute("articles",article);
        return "qna/show";
    }


    @GetMapping("/articles/{id}/form")
    public String updatePost(@PathVariable("id") Long id, Model model, HttpSession session) {
        Article writer = jdbcArticleRepository.findById(id).orElseThrow(null);
        if(isAuthorization(session, writer)) {
            model.addAttribute("modified", writer);
            return "qna/modifiedForm";
        }
        return "redirect:/articles/{id}";
    }

    @PutMapping("/articles/{id}")
    public String updatePost(@ModelAttribute("article") Article article) {
        jdbcArticleRepository.update(article);
        return "redirect:/";
    }

    @DeleteMapping("/articles/{id}/delete")
    public String deletePost(@PathVariable Long id, HttpSession session) {
        Article writer = jdbcArticleRepository.findById(id).orElseThrow();
        if(isAuthorization(session,writer) && isDeletablePost(writer.getAuthorId(), id)){
            jdbcArticleRepository.delete(id);
            jdbcReplyRepository.deletedAll(id);
            return "redirect:/";
        }
        return "redirect:/articles/{id}";
    }

    public boolean isDeletablePost(String memberId,Long articleId){
        List<Reply> replies = jdbcReplyRepository.findAll(articleId);
        // 내가 아닌 경우만 찾기.그럼 나인경우는 안찾아도 된다. 삭제 가능하니까
        return replies.stream().filter(o -> !o.getReplyWriter().equals(memberId)).findFirst().isEmpty(); // 게시글 작성자와 댓글 작성자가 다른 아이디면 false, 같으면 true
    }

    public boolean isAuthorization(HttpSession session, Article article) {
        Member value = (Member) session.getAttribute(MemberSessionUser.LOGIN_MEMBER);
        String loginId = value.getUserId();
        String writerId = article.getAuthorId();
        return loginId.equals(writerId);
    }


}

