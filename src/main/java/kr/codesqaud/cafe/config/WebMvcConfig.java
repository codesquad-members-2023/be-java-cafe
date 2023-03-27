package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.interceptor.AddressInterceptor;
import kr.codesqaud.cafe.interceptor.CacheInvalidInterceptor;
import kr.codesqaud.cafe.interceptor.LoginInterceptor;
import kr.codesqaud.cafe.interceptor.UpdateUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/qna/**")
                .addPathPatterns("/users/updateUser/**");

        registry.addInterceptor(new CacheInvalidInterceptor())
                .addPathPatterns("/qna/**");

        registry.addInterceptor(new AddressInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/users/**", "/qna/**", "/error/error", "/fragment/**", "/welcome/**")
                .excludePathPatterns("/css/**", "/fonts/**", "/images/**", "/js/**");

        registry.addInterceptor(new UpdateUserInterceptor())
                .addPathPatterns("/users/updateUser/**");
    }
}
