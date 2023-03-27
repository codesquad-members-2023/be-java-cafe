package kr.codesqaud.cafe.interceptor;


import kr.codesqaud.cafe.AddressException;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddressInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        throw new AddressException("올바르지 못한 주소입니다.");
    }
}
