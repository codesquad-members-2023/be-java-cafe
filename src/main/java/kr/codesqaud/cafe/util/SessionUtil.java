package kr.codesqaud.cafe.util;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionUtil {

    public static User getSessionedUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

}
