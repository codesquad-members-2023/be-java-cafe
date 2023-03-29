package kr.codesqaud.cafe.filter;


import kr.codesqaud.cafe.controller.SessionConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PermissionFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        logger.debug("권한 확인 로직 실행 : " + httpServletRequest.getRequestURI());
        //현재 세션이 있는지 확인하고 있으면 기존 세션 반환 없으면 null 반환
        HttpSession session = httpServletRequest.getSession(false);

        //세션이 없거나 세션에 로그인유저가 없으면
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            logger.debug("미인증 사용자 요청");
            httpServletResponse.sendRedirect("/user/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
