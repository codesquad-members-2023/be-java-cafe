package kr.codesqaud.cafe.cafeservice.Interceptor;

import kr.codesqaud.cafe.cafeservice.controller.MemberController;
import kr.codesqaud.cafe.cafeservice.session.SessionConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    //세션이 존재하지 않거나 세션에 저장된 로그인 정보가 없는 경우, 로그인이 되어 있지 않은 사용자로 판단하여 로그 출력 후, 로그인 페이지로 redirect 합니다.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.debug("login인터셉터{}", requestURI);
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.debug("미인증 사용자 요청");
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
