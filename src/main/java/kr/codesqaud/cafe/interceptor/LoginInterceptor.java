package kr.codesqaud.cafe.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static kr.codesqaud.cafe.Constants.SESSIONED_USER;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	private final Logger log = LoggerFactory.getLogger(getClass());

	public List loginNecessary
		= Arrays.asList("/articles/**", "/users/list", "/users/update/**", "/qna/**", "*");

	public List loginUnnecessary
		= Arrays.asList("/login", "/users/add", "/");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		Long loginUserSequence = (Long) request.getSession().getAttribute(SESSIONED_USER);

		if (loginUserSequence != null) {
			log.info("Login Confirmed");
			return true;
		}

		log.info("Login First");
		String destinationUri = request.getRequestURI();
		String destinationQuery = request.getQueryString();
		String destination = (destinationQuery == null) ? destinationUri : destinationUri + "?" + destinationQuery;
		request.getSession().setAttribute("destination", destination);

		response.sendRedirect("/login");

		return false;
	}
}
