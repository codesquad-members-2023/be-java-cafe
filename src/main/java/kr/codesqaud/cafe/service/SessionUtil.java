package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    private static final String LOGIN_USER = "loginUser";

    /**
     * 세션 유저 조회
     */
    public static Object getUserInfo(HttpSession session) {
        return session.getAttribute(LOGIN_USER);
    }

    public static void setUserInfo(HttpSession session, User loginUser) {
        session.setAttribute(LOGIN_USER, loginUser);
    }
}
