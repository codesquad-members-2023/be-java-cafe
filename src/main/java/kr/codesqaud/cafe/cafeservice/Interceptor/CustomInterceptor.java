package kr.codesqaud.cafe.cafeservice.Interceptor;

import kr.codesqaud.cafe.cafeservice.controller.MemberController;
import kr.codesqaud.cafe.cafeservice.session.SessionConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CustomInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("login인터셉터{}", requestURI);
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
