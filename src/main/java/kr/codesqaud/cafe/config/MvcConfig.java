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

        // 맵핑 간략화
        registry.addViewController("/user/form").setViewName("user/form");
        registry.addViewController("/qna/form").setViewName("qna/form");
        registry.addViewController("/user/login").setViewName("user/login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        int interceptorExecutionOrder = 1;
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(interceptorExecutionOrder)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/user/login", "/login", "/user/login_failed"
                        , "/fonts/**", "/js/**", "/images/**", "/css/**", "/*.ico", "/error");
    }
}
