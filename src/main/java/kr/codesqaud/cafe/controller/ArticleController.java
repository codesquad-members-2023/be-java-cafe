package kr.codesqaud.cafe.controller;


import kr.codesqaud.cafe.basic.Article;
import kr.codesqaud.cafe.basic.Reply;
import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.config.ConstantConfig;
import kr.codesqaud.cafe.exception.gobalExeption.NotFoundException;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.DataBaseRepository.DatabaseReplyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    ArticleRepository articleRepository;
    DatabaseReplyRepository replyRepository;

    public ArticleController(ArticleRepository articleRepository, DatabaseReplyRepository replyRepository) {
        this.articleRepository = articleRepository;
        this.replyRepository = replyRepository;
    }

    @GetMapping("/create")
    public String create() {
        return "qna/create";
    }

    @PostMapping("/create")
    public String create(@RequestParam String title,
                         @RequestParam String contents,
                         @SessionAttribute(name = ConstantConfig.SESSION_ID) String userId) {
        articleRepository.save(new Article(userId, title, contents));
        return "redirect:/qna/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Article> articles = new ArrayList<>(articleRepository.findAll());

        model.addAttribute("articles", articles);
        return "qna/list";
    }

    @GetMapping("/show/{articleId}")
    public String show(@PathVariable int articleId,
                       Model model) {
        // TODO : 단순히 게시글을 찾는 로직이지만, 코드가 길어지는 뜻한 느낌이 있음
        // TODO : 서비스 계층에서 처리하는 것을 고려할 예정 (예외는 컨트롤에서 하는 것이 좋다고 들었는데, 예외는 어떻게 처리?)
        Optional<Article> articleOptional = articleRepository.findByArticleId(articleId);
        if (articleOptional.isEmpty()) throw new NotFoundException("게시글을 찾지 못했습니다.");
        Article article = articleOptional.get();
        List<Reply> replies = replyRepository.findByArticleId(articleId);
        model.addAttribute("article", article);
        model.addAttribute("replies", replies);
        return "qna/show";
    }

    @GetMapping("/updateForm/{articleId}")
    public String update(@PathVariable int articleId,
                         @SessionAttribute(name = ConstantConfig.SESSION_ID) User user,
                         Model model) {
        // TODO : 단순히 게시글을 찾는 로직이지만, 코드가 길어지는 뜻한 느낌이 있음
        // TODO : 서비스 계층에서 처리하는 것을 고려할 예정 (예외는 컨트롤에서 하는 것이 좋다고 들었는데, 예외는 어떻게 처리?)
        Optional<Article> articleOptional = articleRepository.findByArticleId(articleId);
        if (articleOptional.isEmpty()) throw new NotFoundException("게시글을 찾지 못했습니다.");
        Article article = articleOptional.get();

        // TODO : 검증 공부 후 다시 리펙토링 할 예정
        // TODO : 그냥 리스트로 돌아가는 것이 아닌, 명시적인 에러처리를 해야함
        if (!article.getWriter().equals(user.getUserId())) {
            return "redirect:/qna/list";
        }

        model.addAttribute(article);
        return "qna/updateForm";
    }

    @PostMapping("/update/{articleId}")
    public String update(@PathVariable int articleId,
                         @RequestParam String title,
                         @RequestParam String contents) {
        articleRepository.update(articleId, title, contents);
        return "redirect:/qna/show/" + articleId;
    }

    @DeleteMapping("/delete/{articleId}")
    public String delete(@PathVariable int articleId,
                         @SessionAttribute(name = ConstantConfig.SESSION_ID) User user) {
        // TODO : 단순히 게시글을 찾는 로직이지만, 코드가 길어지는 뜻한 느낌이 있음
        // TODO : 서비스 계층에서 처리하는 것을 고려할 예정 (예외는 컨트롤에서 하는 것이 좋다고 들었는데, 예외는 어떻게 처리?)
        Optional<Article> articleOptional = articleRepository.findByArticleId(articleId);
        if (articleOptional.isEmpty()) throw new NotFoundException("게시글을 찾지 못했습니다");
        Article article = articleOptional.get();

        // TODO : 검증 공부 후 다시 리펙토링 할 예정
        // TODO : 그냥 리스트로 돌아가는 것이 아닌, 명시적인 에러처리를 해야함
        if (article.getWriter().equals(user.getUserId())) {
            return "redirect:/qna/list";
        }
        articleRepository.delete(articleId);
        return "redirect:/qna/list";
    }


}
