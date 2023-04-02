package kr.codesqaud.cafe.interceptor;

import kr.codesqaud.cafe.exception.InvalidAuthorityException;
import kr.codesqaud.cafe.util.SessionUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static kr.codesqaud.cafe.exception.InvalidAuthorityException.NO_SESSION_USER;

public class AuthorityInterceptor implements HandlerInterceptor {
    public static final String[] LOGIN_ESSENTIAL = {"/users/**", "/articles/**", "/questions/**"};
    public static final String[] LOGIN_INESSENTIAL = {"/", "/login", "/users/form", "/users/login", "/users/join"};
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SessionUser sessionUser = SessionUser.getSessionUser(request.getSession(false));
        if (sessionUser==null) {
            throw new InvalidAuthorityException(NO_SESSION_USER);
        }
            return true;
    }


}
