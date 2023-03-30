package kr.codesqaud.cafe;

import kr.codesqaud.cafe.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		LoginInterceptor loginInterceptor = new LoginInterceptor();
		registry.addInterceptor(loginInterceptor)
			.addPathPatterns(loginInterceptor.loginNecessary)
			.excludePathPatterns(loginInterceptor.loginUnnecessary);
	}
}
