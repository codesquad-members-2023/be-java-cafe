package kr.codesqaud.cafe.interceptor;

import kr.codesqaud.cafe.dto.SessionUser;
import kr.codesqaud.cafe.exception.ExceptionStatus;
import kr.codesqaud.cafe.exception.InvalidAuthorityException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorityInterceptor implements HandlerInterceptor {
    public static final String[] LOGIN_ESSENTIAL = {"/users/**", "/articles/**", "/questions/**"};
    public static final String[] LOGIN_INESSENTIAL = {"/", "/login", "/users/form", "/users/login", "/users/join"};
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SessionUser sessionUser = SessionUser.getSessionUser(request.getSession(false));
        if (sessionUser==null) {
            throw new InvalidAuthorityException(ExceptionStatus.NO_SESSION_USER);
        }
            return true;
    }


}
