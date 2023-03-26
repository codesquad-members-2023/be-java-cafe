package kr.codesqaud.cafe.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.codesqaud.cafe.exceptions.UserInfoException;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private ArticleController articleController;

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
