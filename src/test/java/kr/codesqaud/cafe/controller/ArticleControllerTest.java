package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.SessionConstant;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.dto.ArticleWithWriter;
import kr.codesqaud.cafe.domain.dto.ReplyWithUser;
import kr.codesqaud.cafe.domain.dto.SimpleArticleWithWriter;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.MySQLReplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@WebMvcTest(value = ArticleController.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private MySQLReplyRepository replyRepository;

    private MockHttpSession mockHttpSession;

    @BeforeEach
    private void setup() {
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(SessionConstant.LOGIN_USER_ID, 1);
        mockHttpSession.setAttribute(SessionConstant.LOGIN_USER_NICKNAME, "hyun");
    }

    @Test
    @DisplayName("홈(/)으로 요청하면 모든 게시글을 보여준다.")
    void showArticles() throws Exception {
        SimpleArticleWithWriter article = new SimpleArticleWithWriter(1, "test", LocalDateTime.now(), 1, "hyun", 3);
        BDDMockito.given(articleRepository.findAll()).willReturn(Collections.singletonList(article));

        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("articles"))
                .andExpect(MockMvcResultMatchers.model().attribute("articles", Collections.singletonList(article)))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("게시글 작성버튼을 누르면 게시글제목과 게시글내용을 파라미터로 보낸다.")
    void question() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("title", "실화냐?");
        paramMap.add("contents", "미안하다 이거 보여주려고 어그로 끌었다.");

        mvc.perform(MockMvcRequestBuilders.post("/questions").params(paramMap).session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));
    }

    @Test
    @DisplayName("게시글을 클릭 하면 제목과 내용 상세정보를 확인할 수 있다.")
    void showDetailedArticle() throws Exception {
        ArticleWithWriter detailedArticle = new ArticleWithWriter(1, "제목", "내용텟", LocalDateTime.now(), 1, "hyun", 3);
        BDDMockito.given(articleRepository.findById(1)).willReturn(detailedArticle);

        ReplyWithUser reply1 = new ReplyWithUser(1, 1, "hyun", "댓글1", LocalDateTime.now());
        ReplyWithUser reply2 = new ReplyWithUser(2, 2, "yoon", "댓글2", LocalDateTime.now());
        ReplyWithUser reply3 = new ReplyWithUser(3, 1, "hyun", "댓글3", LocalDateTime.now());

        List<ReplyWithUser> replies = List.of(reply1, reply2, reply3);
        BDDMockito.given(replyRepository.findByArticleId(1)).willReturn(replies);


        mvc.perform(MockMvcRequestBuilders.get("/articles/1").session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.model().attributeExists("article"))
                .andExpect(MockMvcResultMatchers.model().attribute("article", detailedArticle))
                .andExpect(MockMvcResultMatchers.model().attributeExists("replies"))
                .andExpect(MockMvcResultMatchers.model().attribute("replies", replies))
                .andExpect(MockMvcResultMatchers.view().name("qna/show"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("게시글 삭제 버튼을 누르면 게시글을 삭제할 수 있다.")
    void deleteArticle() throws Exception {
        ArticleWithWriter mockArticle = new ArticleWithWriter(1, "제목", "내용텟", LocalDateTime.now(), 1, "hyun", 3);
        BDDMockito.given(articleRepository.findById(1)).willReturn(mockArticle);
        BDDMockito.doNothing().when(articleRepository).delete(1);

        mvc.perform(MockMvcRequestBuilders.delete("/articles/1").session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @DisplayName("게시글 수정 버튼을 누르면 작성된 게시글을 내용을 바탕으로 수정할 수 있다.")
    void showArticleUpdateForm() throws Exception {
        ArticleWithWriter mockArticle = new ArticleWithWriter(1, "제목", "내용텟", LocalDateTime.now(), 1, "hyun", 3);

        BDDMockito.given(articleRepository.findById(1)).willReturn(mockArticle);

        mvc.perform(MockMvcRequestBuilders.get("/articles/1/form").session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.model().attributeExists("article"))
                .andExpect(MockMvcResultMatchers.model().attribute("article", mockArticle))
                .andExpect(MockMvcResultMatchers.view().name("qna/updateForm"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("게시글 작성자가 게시글 수정 폼을 제출하면 수정이 완료된다.")
    void updateArticleSuccess() throws Exception {
        ArticleWithWriter mockArticle = new ArticleWithWriter(1, "제목", "내용텟", LocalDateTime.now(), 1, "hyun", 3);

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("title", "수정한 제목");
        paramMap.add("contents", "수정한 내용");

        BDDMockito.given(articleRepository.findById(1)).willReturn(mockArticle);
        BDDMockito.doNothing().when(articleRepository).update(1, new Article(1, paramMap.getFirst("title"), paramMap.getFirst("contents")));

        mvc.perform(MockMvcRequestBuilders.put("/articles/1").params(paramMap).session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/articles/{index}"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @DisplayName("게시글 작성자가 아니면 게시글을 수정할 수 없다.")
    void updateArticleFail() throws Exception {
        ArticleWithWriter mockArticle = new ArticleWithWriter(1, "제목", "내용텟", LocalDateTime.now(), 2, "yoon", 3);

        BDDMockito.given(articleRepository.findById(1)).willReturn(mockArticle);

        mvc.perform(MockMvcRequestBuilders.get("/articles/1/form").session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", "[ERROR] 자신이 작성하지 않은 게시물은 수정할 수 없습니다."))
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
