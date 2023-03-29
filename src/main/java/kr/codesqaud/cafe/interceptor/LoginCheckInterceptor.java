package kr.codesqaud.cafe.interceptor;

import kr.codesqaud.cafe.config.ConstantConfig;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String requestURL = request.getRequestURI();
        if (session == null || session.getAttribute(ConstantConfig.SESSION_ID) == null) {
            response.sendRedirect("/user/login?requestURL=" + requestURL);
            return false;
        }
        return true;
    }
}
