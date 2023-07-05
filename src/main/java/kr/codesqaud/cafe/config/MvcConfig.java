package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addViewController("/form").setViewName("user/form");
        registry.addViewController("/qna/questions").setViewName("qna/form");
    }

    @Override // 명시적으로 오버라이드
    public void addInterceptors(InterceptorRegistry registry) { // 인터셉터 등록
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(Ordered.HIGHEST_PRECEDENCE)
                .addPathPatterns("/users/**","/articles/**")
                .excludePathPatterns("/", "/login", "/css/**", "/images/**", "/error","/fonts/**", "/logout","/form","/users","/articles");
    }
}
