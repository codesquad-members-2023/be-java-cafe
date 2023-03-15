package kr.codesqaud.cafe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import kr.codesqaud.cafe.domain.ArticleRepository;
import kr.codesqaud.cafe.domain.JoinService;
import kr.codesqaud.cafe.domain.JoinServiceImpl;
import kr.codesqaud.cafe.domain.MemoryArticleRepository;
import kr.codesqaud.cafe.domain.MemoryUserRepository;
import kr.codesqaud.cafe.domain.QnaService;
import kr.codesqaud.cafe.domain.QnaServiceImpl;
import kr.codesqaud.cafe.domain.UserRepository;

@Configuration
public class AutoAppConfig implements WebMvcConfigurer {
    @Bean
    public JoinService joinService() {
        return new JoinServiceImpl(userRepository());
    }

    @Bean
    public UserRepository userRepository() {
        return new MemoryUserRepository();
    }

    @Bean
    public QnaService qnaService() {
        return new QnaServiceImpl(articleRepository());
    }
    @Bean
    public ArticleRepository articleRepository() {
        return new MemoryArticleRepository();
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/users/form").setViewName("user/form");
        registry.addViewController("/users/login").setViewName("user/login");
        registry.addViewController("/users/profile").setViewName("user/profile");

        //registry.addViewController("/qna/form").setViewName("qna/form");
    }
}
