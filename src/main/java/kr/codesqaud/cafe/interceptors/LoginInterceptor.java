package kr.codesqaud.cafe.interceptors;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws
            IOException {
        HttpSession httpSession = request.getSession();

        if (httpSession.getAttribute("sessionedUser") == null) {
            response.sendRedirect("/users/login_failed");

            return false;
        }
        return true;
    }
}
