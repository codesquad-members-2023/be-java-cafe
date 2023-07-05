package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.SessionConstant;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.MySQLReplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ReplyController.class)
class ReplyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MySQLReplyRepository replyRepository;

    private MockHttpSession mockHttpSession;

    @BeforeEach
    public void setup() {
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(SessionConstant.LOGIN_USER, new User(1, "hyun", "1234" , "황현", "ghkdgus29@naver.com"));
    }

    @Test
    @DisplayName("댓글 답변하기 버튼을 누르면 새로운 댓글정보가 JSON으로 전달된다.")
    void reply() throws Exception {
        Reply mockReply = new Reply("텟텟", 1, 1);

        mvc.perform(MockMvcRequestBuilders.post("/articles/1/replies")
                        .param("contents", mockReply.getContents())
                        .session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("hyun"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contents").value("텟텟"));
    }

    @Test
    @DisplayName("자신이 작성한 댓글은 댓글 삭제 버튼을 눌러 삭제할 수 있다.")
    void deleteSuccess() throws Exception {
        Reply mockReply = new Reply("텟텟", 1, 1);
        BDDMockito.given(replyRepository.findById(1)).willReturn(mockReply);

        mvc.perform(MockMvcRequestBuilders.delete("/articles/1/replies/1")
                        .param("replyId", "1")
                        .session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("성공"));
    }

    @Test
    @DisplayName("자신이 작성하지 않은 댓글은 삭제할 수 없다.")
    void deleteFail() throws Exception {
        Reply mockReply = new Reply("텟텟", 2, 1);
        BDDMockito.given(replyRepository.findById(1)).willReturn(mockReply);

        mvc.perform(MockMvcRequestBuilders.delete("/articles/1/replies/1")
                        .param("replyId", "1")
                        .session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("실패이유 : [ERROR] 자신이 작성하지 않은 댓글은 삭제할 수 없습니다."));
    }
}
