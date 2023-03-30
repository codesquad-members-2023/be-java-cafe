package kr.codesqaud.cafe.cafeservice.config;

import kr.codesqaud.cafe.cafeservice.Interceptor.LoginInterceptor;
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

        registry.addViewController("/users/form").setViewName("user/form");
        registry.addViewController("/users/login").setViewName("user/login");
        registry.addViewController("/questions/form").setViewName("qna/form");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(Ordered.HIGHEST_PRECEDENCE)
                .addPathPatterns("/users/**", "/articles/**", "/questions/**")
                .excludePathPatterns("/", "/login", "/logout"
                        , "/fonts/**", "/js/**", "/images/**", "/css/**", "/*.ico", "/error");
    }
}
