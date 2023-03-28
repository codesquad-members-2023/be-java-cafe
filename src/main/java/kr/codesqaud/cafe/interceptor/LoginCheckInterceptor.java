package kr.codesqaud.cafe.interceptor;

import kr.codesqaud.cafe.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession();

        if (session == null || session.getAttribute("loginUser") == null) {
            log.info("미인증 사용자 요청");

            // 회원가입 실패시 회원가입 실패 페이지로 redirect
            if (requestURI.equals("/users")){
                log.info("회원가입 실패");

                response.sendRedirect("/user/form_failed");
                return false;
            }

            // 로그인이 필요한 페이지 접근시 로그인 페이지로 redirect
            response.sendRedirect("/user/login");
            return false;
        }

        return true;
    }
}
