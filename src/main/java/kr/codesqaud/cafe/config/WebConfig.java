package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.interceptor.LoginInterceptor;
import kr.codesqaud.cafe.interceptor.CacheInvalidateInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(Ordered.HIGHEST_PRECEDENCE)
                .addPathPatterns("/questions/form", "/articles/**", "/users/*")
                .excludePathPatterns("/users/form", "/css/**", "/*.ico", "/fonts/**", "/images/**", "/js/**");

        registry.addInterceptor(new CacheInvalidateInterceptor())
                .addPathPatterns("/articles/*")
                .excludePathPatterns("/css/**", "/*.ico", "/fonts/**", "/images/**", "/js/**");
    }
}
