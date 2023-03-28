package kr.codesqaud.cafe.interceptor;

import kr.codesqaud.cafe.dto.SessionUser;
import kr.codesqaud.cafe.exception.ExceptionStatus;
import kr.codesqaud.cafe.exception.InvalidAuthorityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class AuthorityInterceptor implements HandlerInterceptor {
    public static final String[] LOGIN_ESSENTIAL = {"/users/**", "/articles/**", "/questions/**"};
    public static final String[] LOGIN_INESSENTIAL = {"/", "/login", "/users/form", "/users/login", "/users/join"};
    private static final String[] PRIVATE_PATH = {
            "/users/\\d+/update",
            "/articles/\\d+/update", "/articles/\\d+/delete"
    };

    Logger LOG = LoggerFactory.getLogger(AuthorityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SessionUser sessionUser = SessionUser.getSessionUser(request.getSession(false));
        if (sessionUser==null || sessionUser.getId() == 0) {
            throw new InvalidAuthorityException(ExceptionStatus.NO_SESSION_USER);
        }
        return true;
    }

    // 인터셉터는 요청을 처리한 직후 뷰를 생성하기 전에 이 메서드를 호출합니다.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        SessionUser sessionUser = SessionUser.getSessionUser(request.getSession(false));
        String requestURI = request.getRequestURI();

        if (!isPrivatePath(requestURI)) {
            return;
        }

        if (sessionUser.getId() != getParseUriId(requestURI)) {
            throw new InvalidAuthorityException(ExceptionStatus.INVALID_MEMBER);
        }
    }

    private boolean isPrivatePath(String requestURI) {
        return Arrays.stream(PRIVATE_PATH).anyMatch(requestURI::matches);
    }

    private long getParseUriId(String requestURI) {
        return Long.parseLong(requestURI.replaceAll("[^\\d+]", ""));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
