package kr.codesqaud.cafe;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.JdbcArticleRepository;
import kr.codesqaud.cafe.repository.JdbcUserRepository;
import kr.codesqaud.cafe.service.JoinService;
import kr.codesqaud.cafe.service.impl.JoinServiceImpl;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import kr.codesqaud.cafe.service.QnaService;
import kr.codesqaud.cafe.service.impl.QnaServiceImpl;
import kr.codesqaud.cafe.repository.UserRepository;

@Configuration
public class AutoAppConfig implements WebMvcConfigurer {
    private final DataSource dataSource;
    @Bean
    public JoinService joinService() {
        return new JoinServiceImpl(userRepository());
    }

    @Bean
    public UserRepository userRepository() {
        return new JdbcUserRepository(dataSource);
    }

    @Bean
    public QnaService qnaService() {
        return new QnaServiceImpl(articleRepository());
    }

    @Bean
    public ArticleRepository articleRepository() {
        return new JdbcArticleRepository(dataSource);
        //return new MemoryArticleRepository();
    }


    public AutoAppConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/users/form").setViewName("user/form");
        registry.addViewController("/users/login").setViewName("user/login");
        registry.addViewController("/users/profile").setViewName("user/profile");

        registry.addViewController("/qna/form").setViewName("qna/form");
    }
}
