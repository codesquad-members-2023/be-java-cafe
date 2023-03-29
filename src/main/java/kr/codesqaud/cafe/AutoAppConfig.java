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
import kr.codesqaud.cafe.repository.JdbcReplyRepository;
import kr.codesqaud.cafe.repository.JdbcUserRepository;
import kr.codesqaud.cafe.repository.ReplyRepository;
import kr.codesqaud.cafe.repository.UserRepository;

@Configuration
public class AutoAppConfig implements WebMvcConfigurer {
    private final DataSource dataSource;
    private final HandlerInterceptor loginInterceptor;
    private final HandlerInterceptor loggerInterceptor;
    private final HandlerInterceptor cacheInvalidator;
    private final HandlerInterceptor invalidAddressInterceptor;

    public AutoAppConfig(DataSource dataSource, HandlerInterceptor loginInterceptor,
            HandlerInterceptor loggerInterceptor, HandlerInterceptor cacheInvalidator,
            HandlerInterceptor invalidAddressInterceptor) {
        this.dataSource = dataSource;
        this.loginInterceptor = loginInterceptor;
        this.loggerInterceptor = loggerInterceptor;
        this.cacheInvalidator = cacheInvalidator;
        this.invalidAddressInterceptor = invalidAddressInterceptor;
    }

    @Bean
    public UserRepository userRepository() {
        return new JdbcUserRepository(dataSource);
    }

    @Bean
    public ArticleRepository articleRepository() {
        return new JdbcArticleRepository(dataSource);
    }

    @Bean
    public ReplyRepository replyRepository() {
        return new JdbcReplyRepository(dataSource);
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
        final int midPrecedence = 0;
        final String[] invalidAddressPattern = {"/", "/users/**", "/qna/**", "/css/**", "/*.ico", "/js/**", "/images/**",
                "/fonts/**", "/api/error"};
        final String[] openAddressPattern = {"/users/login_failed", "/users/form",
                "/users/login", "/users/create", "/api/error"};
        final String[] staticsPattern = {"/css/**", "/*.ico", "/js/**", "/images/**", "/fonts/**"};
        final String[] closeAddressPattern = {"/users/**", "/qna/**"};

        registry.addInterceptor(invalidAddressInterceptor)
                .order(Ordered.HIGHEST_PRECEDENCE)
                .addPathPatterns("/**")//모든 URL에 대해서, 404를 발생.
                .excludePathPatterns(invalidAddressPattern);
        registry.addInterceptor(loginInterceptor)
                .order(midPrecedence)
                .addPathPatterns(closeAddressPattern)
                .excludePathPatterns(openAddressPattern);
        registry.addInterceptor(loggerInterceptor)
                .order(Ordered.LOWEST_PRECEDENCE)
                .addPathPatterns("/**")
                .excludePathPatterns(staticsPattern);
        registry.addInterceptor(cacheInvalidator)
                .order(Ordered.LOWEST_PRECEDENCE)
                .addPathPatterns("/**")
                .excludePathPatterns(staticsPattern);
    }
}
