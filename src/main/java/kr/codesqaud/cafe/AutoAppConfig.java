package kr.codesqaud.cafe;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.JdbcArticleRepository;
import kr.codesqaud.cafe.repository.JdbcUserRepository;

import kr.codesqaud.cafe.repository.UserRepository;

@Configuration
public class AutoAppConfig implements WebMvcConfigurer {

    private final DataSource dataSource;
    private final HandlerInterceptor loginInterceptor;

    public AutoAppConfig(DataSource dataSource, HandlerInterceptor handlerInterceptor) {
        this.dataSource = dataSource;
        loginInterceptor = handlerInterceptor;
    }


    @Bean
    public UserRepository userRepository() {
        return new JdbcUserRepository(dataSource);
    }


    @Bean
    public ArticleRepository articleRepository() {
        return new JdbcArticleRepository(dataSource);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/users/form").setViewName("user/form");
        registry.addViewController("/users/profile").setViewName("user/profile");

        registry.addViewController("/qna/form").setViewName("qna/form");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
            .addPathPatterns("/users/**", "/qna/**")
            .excludePathPatterns("/users/login_failed", "/users/form",
                "/users/login", "/users/create");
    }
}
