package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;

import javax.servlet.http.HttpSession;

public class UserSession {
    public static final String LOGIN_USER = "loginUser";

    public static User getAttribute(HttpSession session) {
        return (User) session.getAttribute(UserSession.LOGIN_USER);
    }
}
