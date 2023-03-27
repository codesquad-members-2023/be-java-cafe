package kr.codesqaud.cafe.service;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static final String LOGIN_USER = "loginUser";

    /**
     * 세션 유저 조회
     */
    public Object getUserInfo(HttpSession session){
        return session.getAttribute(LOGIN_USER);
    }
}
