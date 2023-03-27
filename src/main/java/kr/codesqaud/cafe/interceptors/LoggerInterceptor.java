package kr.codesqaud.cafe.interceptors;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.codesqaud.cafe.controller.ArticleController;
import kr.codesqaud.cafe.controller.UserController;

@Component
public class LoggerInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {

        if (logger.isDebugEnabled()) {
            logger.debug("실행 - Request URI : {} 실행 메서드 명 : {}",request.getRequestURI(), request.getMethod());
        }

        //return HandlerInterceptor.super.preHandle(request, response, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        //HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        if (logger.isDebugEnabled()) {
            logger.debug("실행 완료 - Request URI : {} 실행 메서드 명 : {}",request.getRequestURI(), request.getMethod());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
        //HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        if (logger.isDebugEnabled()) {
            logger.debug("Completion - Request URI : {} 실행 메서드 명 : {}",request.getRequestURI(), request.getMethod());
        }
    }
}
