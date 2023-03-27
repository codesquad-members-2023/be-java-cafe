package kr.codesqaud.cafe.interceptors;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.codesqaud.cafe.controller.ArticleController;
import kr.codesqaud.cafe.controller.UserController;

@Component
public class CacheInvalidator implements HandlerInterceptor {
    private UserController userController;
    private ArticleController articleController;

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws
            IOException {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        return true;
    }
}
