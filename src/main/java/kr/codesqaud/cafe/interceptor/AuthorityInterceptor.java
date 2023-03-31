package kr.codesqaud.cafe.interceptor;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.SessionUser;
import kr.codesqaud.cafe.exception.ExceptionStatus;
import kr.codesqaud.cafe.exception.InvalidAuthorityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorityInterceptor implements HandlerInterceptor {
    public static final String[] LOGIN_ESSENTIAL = {"/users/**", "/articles/**", "/questions/**"};
    public static final String[] LOGIN_INESSENTIAL = {"/", "/login", "/users/form", "/users/login", "/users/join"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (SessionUser.getSessionUser(request.getSession(false))==null) {
            throw new InvalidAuthorityException(ExceptionStatus.NO_SESSION_USER);
        }
            return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        SessionUser sessionUser = SessionUser.getSessionUser(request.getSession(false));
        String requestURI = request.getRequestURI();


        if (requestURI.matches("/users/\\d+/update") && sessionUser.getId() != getParseUriId(requestURI)) {
            throw new InvalidAuthorityException(ExceptionStatus.INVALID_MEMBER);
        }

        if (requestURI.matches("/articles/\\d+/update") || requestURI.matches("/articles/\\d+/delete") ) {
            Article article = (Article) modelAndView.getModel().get("article");
            if (article.getWriterId() != sessionUser.getId()) {
                throw new InvalidAuthorityException(ExceptionStatus.INVALID_WRITER);
            }
        }

    }

    private long getParseUriId(String requestURI) {
        return Long.parseLong(requestURI.replaceAll("[^\\d+]", ""));
    }
}
