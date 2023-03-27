package kr.codesqaud.cafe.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import kr.codesqaud.cafe.model.User;
import kr.codesqaud.cafe.exceptions.UserInfoException;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

@ContextConfiguration(classes = TestConfig.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JoinService joinService;

    @Test
    @DisplayName("유저 프로필을 조회하면 ID가 일치하는 회원을 모델에 저장하고 뷰로 표시한다.")
    void userProfileTest() throws Exception {
        User poro = new User("poro", "123", "포로", "ngw7617@naver.com", 1);

        given(joinService.lookupUser("poro")).willReturn(poro);

        mvc.perform(get("/users/poro"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/profile"))
                .andExpect(model().attributeExists("user"))
        ;
    }

    @Test
    @DisplayName("유저 목록을 조회하면 모든 유저 목록을 모델에 저장하고 회원 목록의 뷰를 표시한다.")
    void userListTest() throws Exception {
        User poro = new User("poro", "123", "포로", "ngw7617@naver.com", 1);

        given(joinService.lookupAllUser()).willReturn(Collections.singletonList(poro));

        mvc.perform(get("/users/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", Collections.singletonList(poro)))
        ;
    }

    @Test
    @DisplayName("회원 가입을 하면 회원 목록으로 리다이렉트한다.")
    void userAdd() throws Exception {
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("userId", "poro");
        paramsMap.add("password", "123");
        paramsMap.add("name", "포로");
        paramsMap.add("email", "ngw7617@naver.com");
        paramsMap.add("index", "1");

        mvc.perform(post("/users/create").params(paramsMap))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/list"));

    }

    @Nested
    @DisplayName("UpdateForm에 접근 시")
    class Describe_UpdateForm {
        @Test
        @DisplayName("로그인된 상태이면 자신의 회원 정보 수정 폼에 접근할 수 있다.")
        void userUpdateFormTest() throws Exception {
            MockHttpSession mockHttpSession = new MockHttpSession();
            //given : 로그인 된 상태
            mockHttpSession.setAttribute("sessionedUser", "poro");

            mvc.perform(get("/users/poro/form").session(mockHttpSession))
                    .andExpect(status().isOk())
                    .andExpect(view().name("user/updateForm"))
                    .andExpect(model().attributeExists("userId"))
                    .andExpect(model().attribute("userId", "poro"));
        }

        @Test
        @DisplayName("로그인되지 않은 상태면 타인의 회원 정보 수정 폼에 접근하면 예외가 발생된다.")
        void NonMembersUpdateFormTest() throws Exception {
            MockHttpSession mockHttpSession = new MockHttpSession();
            //given : 로그인 되지 않음

            //Advice에서 Catch되므로 assertThrows로 검증할 수 없다.
            mvc.perform(get("/users/honux/form").session(mockHttpSession))
                    .andExpect(status().isOk())
                    .andExpect(view().name("user/login_failed"));
        }

        @Test
        @DisplayName("로그인된 상태라도 타인의 회원 정보 수정 폼에 접근하면 예외가 발생된다.")
        void illegalUserUpdateFormTest() throws Exception {
            MockHttpSession mockHttpSession = new MockHttpSession();
            //given : 로그인 된 상태
            mockHttpSession.setAttribute("sessionedUser", "poro");

            //Advice에서 Catch되므로 assertThrows로 검증할 수 없다.
            mvc.perform(get("/users/honux/form").session(mockHttpSession))
                    .andExpect(status().isOk())
                    .andExpect(view().name("user/login_failed"));
        }
    }

    @Test
    @DisplayName("회원 정보 수정을 누르면 유저 정보를 업데이트할 수 있다.")
    void userUpdateCommit() throws Exception {
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("userId", "poro");
        paramsMap.add("password", "123");
        paramsMap.add("newPassword", "1234");
        paramsMap.add("name", "포로");
        paramsMap.add("email", "ngw7617@naver.com");

        mvc.perform(put("/users/poro/update").params(paramsMap))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/list"));
    }

    @Nested
    @DisplayName("Login 시도 시")
    class Describe_Login {
        @Test
        @DisplayName("일치하는 회원으로 로그인 할 수 있다.")
        void loginTest() throws Exception {
            MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
            paramsMap.add("userId", "poro");
            paramsMap.add("password", "123");
            User poro = new User("poro", "123", "포로", "ngw7617@naver.com", 1);
            given(joinService.lookupUser("poro")).willReturn(poro);

            MockHttpSession mockHttpSession = new MockHttpSession();

            mvc.perform(post("/users/login").session(mockHttpSession).params(paramsMap))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/"));
        }

        @Test
        @DisplayName("비밀번호가 틀리면 로그인에 실패하고 로그인 Fail 화면으로 이동한다.")
        void loginFailedTest() throws Exception {
            MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
            paramsMap.add("userId", "poro");
            paramsMap.add("password", "afk");
            User poro = new User("poro", "123", "포로", "ngw7617@naver.com", 1);
            given(joinService.lookupUser("poro")).willReturn(poro);

            MockHttpSession mockHttpSession = new MockHttpSession();

            mvc.perform(post("/users/login").session(mockHttpSession).params(paramsMap))
                    .andExpect(status().isOk())
                    .andExpect(view().name("user/login_failed"));
        }
    }

    @Test
    @DisplayName("로그아웃하면 세션을 종료하고 홈 화면으로 이동한다.")
    void logout() throws Exception {
        //given : 로그인 된 상태
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("sessionedUser", "poro");

        mvc.perform(get("/users/logout").session(mockHttpSession))
                .andExpect(new ResultMatcher() {
                    @Override
                    public void match(MvcResult result) throws Exception {
                        if (!mockHttpSession.isInvalid()) {
                            throw new UserInfoException();
                        }
                    }
                })
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}
