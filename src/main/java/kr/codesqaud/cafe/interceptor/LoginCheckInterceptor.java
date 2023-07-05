package kr.codesqaud.cafe.interceptor;

import kr.codesqaud.cafe.util.MemberSessionUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute(MemberSessionUser.LOGIN_MEMBER) == null){
            response.sendRedirect("/login"); // user/login이 아니여따..! url의 경로를 지정하는건데 html을 적어서 에러가 나고있었다
            return false;
        }
        return true;
    }

}
