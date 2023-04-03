package kr.codesqaud.cafe.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class InvalidAddressInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            IOException,
            ServletException {
        request.setAttribute("error", "404 NOT FOUND");
        request.getRequestDispatcher("/api/error").forward(request, response);

        return false;
    }


}
