package kr.codesqaud.cafe.utils;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

public class SessionUtils {
    public static String getSessionId(HttpSession httpSession) {
        return (String)httpSession.getAttribute("sessionedUser");
    }
}
