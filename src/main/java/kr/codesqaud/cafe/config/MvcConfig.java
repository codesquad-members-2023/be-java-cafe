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
        //registry.addViewController("/articles/{id}/modified").setViewName("qna/modifiedForm");

    }

    @Override // 명시적으로 오버라이드
    public void addInterceptors(InterceptorRegistry registry) { // 인터셉터 등록
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/css/**", "/images/**", "/error","/fonts/**");
    }
}
