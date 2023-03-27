package kr.codesqaud.cafe.interceptor;

import kr.codesqaud.cafe.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateUserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        User loginedUser = (User) session.getAttribute("user");
        String[] split = request.getRequestURI().split("/");
        String userId = split[split.length -1];

        if (!loginedUser.getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인의 페이지만 수정할 수 있습니다.");
        }
        return true;
    }
}
